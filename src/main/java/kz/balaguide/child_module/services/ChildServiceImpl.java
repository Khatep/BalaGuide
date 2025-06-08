package kz.balaguide.child_module.services;

import jakarta.persistence.EntityNotFoundException;
import kz.balaguide.child_module.repository.ChildRepository;
import kz.balaguide.common_module.core.annotations.ForLog;
import kz.balaguide.common_module.core.entities.*;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.financialoperation.heirs.InsufficientFundsException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.ChildNotBelongToParentException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.ChildNotEnrolledToCourseException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildrenNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import kz.balaguide.course_module.repository.AttendanceRepository;
import kz.balaguide.course_module.repository.GroupRepository;
import kz.balaguide.course_module.repository.LessonRepository;
import kz.balaguide.course_module.services.CourseService;
import kz.balaguide.course_module.services.GroupService;
import kz.balaguide.parent_module.services.ParentService;
import kz.balaguide.payment_module.services.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChildServiceImpl implements ChildService {

    private final ResponseMetadataService responseMetadataService;
    private final ParentService parentService;
    private final PaymentService paymentService;
    private final CourseService courseService;
    private final GroupService groupService;

    private final LessonRepository lessonRepository;
    private final ChildRepository childRepository;
    private final GroupRepository groupRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    public Page<Child> findAll(int page, int size) {

        Page<Child> children = childRepository.findAll(PageRequest.of(page, size));
        if (children.getTotalElements() == 0) {
            throw new ChildrenNotFoundException(
                    responseMetadataService.findByCode(ResponseCode._0101).getMessage()
            );
        }

        return children;
    }

    @Override
    public Child findById(Long id) {

        Optional<Child> child = childRepository.findById(id);
        if (child.isEmpty()) {
            throw new ChildNotFoundException(
                    responseMetadataService.findByCode(ResponseCode._0100).getMessage()
            );
        }

        return child.get();
    }

    @Override
    public Child findByPhoneNumber(String phoneNumber) {
        return childRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ChildNotFoundException(
                        responseMetadataService.findByCode(ResponseCode._0100).getMessage()
                ));
    }

    @Override
    public Child save(Child child) {
        return childRepository.save(child);
    }

    @Override
    public Child update(Long id, Child updatedChild) {

        Optional<Child> existingChildOpt = childRepository.findById(id);

        if (existingChildOpt.isEmpty()) {
            throw new ChildNotFoundException(
                    responseMetadataService.findByCode(ResponseCode._0100).getMessage()
            );
        }

        copyNonNullProperties(updatedChild, existingChildOpt.get());

        return childRepository.save(existingChildOpt.get());
    }

    @Override
    public void removeChild(Long id) {
        if (childRepository.existsById(id)) {
            childRepository.deleteById(id);
        } else {
            throw new ChildNotFoundException(
                    responseMetadataService.findByCode(ResponseCode._0100).getMessage()
            );
        }
    }

    @Override
    @ForLog
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<Course> getMyCourses(Child child) {
        Optional<Child> childOpt = childRepository.findById(child.getId());

        if (childOpt.isEmpty()) {
            throw new ChildNotFoundException(
                    responseMetadataService.findByCode(ResponseCode._0100).getMessage()
            );
        }

        Optional<List<Group>> enrolledGroupsOpt = childOpt.map(childEntity ->
                groupRepository.findAllByChildId(childOpt.get().getId())
        );

        if (enrolledGroupsOpt.isEmpty()) {
            throw new ChildNotEnrolledToCourseException(
                    responseMetadataService.findByCode(ResponseCode._0401).getMessage()
            );
        }

        List<Course> courseList = new ArrayList<>();
        for (Group group : enrolledGroupsOpt.get()) {
            courseList.add(group.getCourse());
        }

        return courseList;
    }

    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean enrollChildToCourse(EnrollmentActionDto enrollmentActionDto) {
        Parent parent = parentService.findParentById(enrollmentActionDto.parentId());

        Child child = childRepository.findById(enrollmentActionDto.childId())
                .orElseThrow(() -> new ChildNotFoundException("Child with id: " + enrollmentActionDto.childId() + " not found"));

        Course course = courseService.findCourseById(enrollmentActionDto.courseId())
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + enrollmentActionDto.courseId() + "not found"));

        boolean isParentsChild = child.getParent().equals(parent);

        if (!isParentsChild) {
            throw new ChildNotBelongToParentException("Child does not belong to the specified parent");
        }

        boolean isPaid = paymentService.payForCourse(parent, child, course);

        if (isPaid)
            return courseService.enrollChild(course, child, enrollmentActionDto);
        else
            throw new InsufficientFundsException("Failed to pay for course");
    }

    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean unenrollChildFromCourse(EnrollmentActionDto enrollmentActionDto) {
        Course course = courseService.findCourseById(enrollmentActionDto.childId())
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + enrollmentActionDto.courseId() + " not found"));

        Child child = childRepository.findById(course.getId())
                .orElseThrow(() -> new ChildNotFoundException("Child with id: " + enrollmentActionDto.childId() + " not found"));

        if (!child.getParent().getId().equals(enrollmentActionDto.parentId())) {
            log.error("Child ID {} does not belong to Parent ID {}", enrollmentActionDto.childId(), enrollmentActionDto.parentId());
            throw new ChildNotBelongToParentException("Child does not belong to the specified parent");
        }

        boolean isEnrolledInCourse = groupRepository.isChildEnrolledInCourseGroup(
                enrollmentActionDto.groupId(),
                enrollmentActionDto.parentId()
        );

        if (isEnrolledInCourse) {
            return groupService.unenrollChild(enrollmentActionDto);
        } else {
            log.warn("Child ID {} is not enrolled in course ID {}",
                    enrollmentActionDto.childId(),
                    enrollmentActionDto.courseId()
            );
            return false;
        }
    }

    @Override
    public boolean markAttendanceFromQr(Long childId, Long lessonId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildNotFoundException("Child not found with id: " + childId));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id: " + lessonId));

        boolean alreadyMarked = attendanceRepository.existsByChildIdAndLessonId(childId, lessonId);
        if (alreadyMarked) {
            return false;
        }

        Attendance attendance = Attendance.builder()
                .child(child)
                .lesson(lesson)
                .attended(true)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        attendanceRepository.save(attendance);
        return true;
    }


    /**
     * Copy non-null properties from source Child to target Child.
     */
    private void copyNonNullProperties(Child source, Child target) {
        if (source.getFirstName() != null) target.setFirstName(source.getFirstName());
        if (source.getLastName() != null) target.setLastName(source.getLastName());
        if (source.getBirthDate() != null) target.setBirthDate(source.getBirthDate());
        if (source.getPhoneNumber() != null) target.setPhoneNumber(source.getPhoneNumber());
        if (source.getGender() != null) target.setGender(source.getGender());
        if (source.getParent() != null) target.setParent(source.getParent());
        if (source.getGroupsEnrolled() != null) target.setGroupsEnrolled(source.getGroupsEnrolled());
    }
}
