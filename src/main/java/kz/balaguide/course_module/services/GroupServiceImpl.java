package kz.balaguide.course_module.services;

import kz.balaguide.child_module.repository.ChildRepository;
import kz.balaguide.common_module.core.annotations.ForLog;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.Group;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.ChildNotEnrolledToCourseException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.GroupNotFoundException;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import kz.balaguide.course_module.repository.CourseRepository;
import kz.balaguide.course_module.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ChildRepository childRepository;
    private final CourseRepository courseRepository;

    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean unenrollChild(EnrollmentActionDto enrollmentActionDto) {
        Course course = courseRepository.findById(enrollmentActionDto.childId())
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + enrollmentActionDto.courseId() + " not found"));

        Group group = groupRepository.findById(enrollmentActionDto.courseId())
                .orElseThrow(() -> new GroupNotFoundException("Group with id: " + enrollmentActionDto.groupId() + " not found"));

        childRepository.findById(course.getId())
                .orElseThrow(() -> new ChildNotFoundException("Child with id: " + enrollmentActionDto.childId() + " not found"));

        List<Group> enrolledGroup = groupRepository.findAllByChildId(enrollmentActionDto.childId());

        if (!enrolledGroup.isEmpty()) {
            throw new ChildNotEnrolledToCourseException("Child with id: " + enrollmentActionDto.childId() + " is not enrolled in any courses");
        }

        group.setCurrentParticipants(group.getCurrentParticipants() - 1);
        groupRepository.unenrollChildFromCourseGroup(enrollmentActionDto.courseId(), course.getId());
        return true;
    }
}
