package kz.balaguide.course_module.services.impl;

import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.Group;
import kz.balaguide.common_module.core.entities.Teacher;
import kz.balaguide.course_module.dto.CreateGroupRequest;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import kz.balaguide.course_module.mappers.GroupMapper;
import kz.balaguide.course_module.repository.CourseRepository;
import kz.balaguide.course_module.repository.GroupRepository;
import kz.balaguide.course_module.services.GroupService;
import kz.balaguide.teacher_module.services.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final TeacherService teacherService;

    private final GroupMapper groupMapper;

    @Override
    public boolean unenrollChild(EnrollmentActionDto enrollmentActionDto) {
        Group group = groupRepository.findById(enrollmentActionDto.groupId())
                .orElseThrow();

        groupRepository.unenrollChildFromCourseGroup(enrollmentActionDto.childId(), enrollmentActionDto.groupId());
        group.setCurrentParticipants(group.getCurrentParticipants() - 1);
        groupRepository.save(group);
        return true;
    }

    @Override
    public Group createGroup(CreateGroupRequest createGroupRequest) {
        if (groupRepository.isGroupExistsInCourseByName(createGroupRequest.name(), createGroupRequest.courseId())) {
            log.warn("Group with name: {} already exists in course with id: {}", createGroupRequest.name(), createGroupRequest.courseId());
        }

        Course course = courseRepository.findById(createGroupRequest.courseId())
                .orElseThrow();

        Teacher teacher = teacherService.findTeacherById(createGroupRequest.teacherId());

        Group group = groupMapper.mapCreateGroupRequestToCourse(createGroupRequest, course, teacher);

        //Here call fill lessons method,
        return groupRepository.save(group);
    }


    @Override
    public Optional<Group> findGroupById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public List<Group> findAllGroupsByChildId(Long aLong) {
        return List.of();
    }
}
