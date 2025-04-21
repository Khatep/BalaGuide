package kz.balaguide.course_module.services;

import kz.balaguide.child_module.repository.ChildRepository;
import kz.balaguide.common_module.core.annotations.ForLog;
import kz.balaguide.course_module.dto.CreateCourseRequest;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.common_module.core.entities.Group;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.CourseFullException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.IneligibleChildException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.EducationCenterNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.GroupNotFoundException;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import kz.balaguide.course_module.mappers.CourseMapper;
import kz.balaguide.course_module.repository.CourseRepository;
import kz.balaguide.course_module.repository.GroupRepository;
import kz.balaguide.education_center_module.repository.EducationCenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ChildRepository childRepository;
    private final EducationCenterRepository educationCenterRepository;
    private final GroupRepository groupRepository;
    private final CourseMapper courseMapper;

    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Course createAndSaveCourse(CreateCourseRequest createCourseRequest) {
        EducationCenter educationCenter = educationCenterRepository.findById(createCourseRequest.educationCenterId())
                .orElseThrow(() -> new EducationCenterNotFoundException("Education center with id: " + createCourseRequest.educationCenterId() + " not found") );

        Course course = courseMapper.mapCourseRequestToCourse(createCourseRequest, educationCenter);

        return courseRepository.save(course);
    }

    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Course updateInformation(Long courseId, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + courseId + " not found"));

        courseMapper.mapUpdatedCourseToExisting(existingCourse, updatedCourse);

        return courseRepository.save(existingCourse);
    }

    @Override
    @ForLog
    @Transactional(readOnly = true)
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }


    @Override
    @ForLog
    public boolean removeCourse(Long courseId) {
        childRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + courseId + " not found"));

        childRepository.deleteById(courseId);

        return !childRepository.existsById(courseId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @ForLog
    public boolean enrollChild(Course course, Child child, EnrollmentActionDto enrollmentActionDto) {
        Group group = groupRepository.findById(enrollmentActionDto.groupId())
                .orElseThrow(() -> new GroupNotFoundException("Group with id: " + enrollmentActionDto.groupId() + " not found"));

        if (!isChildEligible(course, child)) {
            throw new IneligibleChildException("Child is not eligible for this course.");
        }
        if (group.isGroupFull()) {
            throw new CourseFullException("Course is full and cannot enroll more participants.");
        }

        child.getGroupsEnrolled().add(group);
        childRepository.save(child);

        group.setCurrentParticipants(group.getCurrentParticipants() + 1);
        groupRepository.save(group);
        return true;
    }

    @Override
    @ForLog
    public List<Course> searchCourses(String query)  {
        return courseRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .sorted()
                .toList();
    }

    @Override
    @ForLog
    public boolean isChildEligible(Course course, Child child) {
        String[] ageRangeParts = course.getAgeRange().split("-");
        int minAge = Integer.parseInt(ageRangeParts[0].trim());
        int maxAge = Integer.parseInt(ageRangeParts[1].trim());
        int childAge = LocalDate.now().getYear() - child.getBirthDate().getYear();

        return childAge >= minAge && childAge <= maxAge;
    }

    @Override
    public Optional<Course> findCourseById(Long id) {
        return courseRepository.findById(id);
    }
}
