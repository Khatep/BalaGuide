
package shared;

import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.Group;
import kz.balaguide.common_module.core.entities.Teacher;
import kz.balaguide.course_module.dto.CreateGroupRequest;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import kz.balaguide.course_module.mappers.GroupMapper;
import kz.balaguide.course_module.repository.CourseRepository;
import kz.balaguide.course_module.repository.GroupRepository;
import kz.balaguide.course_module.services.impl.GroupServiceImpl;
import kz.balaguide.teacher_module.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupServiceImplTest {

    private GroupRepository groupRepository;
    private CourseRepository courseRepository;
    private TeacherService teacherService;
    private GroupMapper groupMapper;
    private GroupServiceImpl groupService;

    @BeforeEach
    void setUp() {
        groupRepository = mock(GroupRepository.class);
        courseRepository = mock(CourseRepository.class);
        teacherService = mock(TeacherService.class);
        groupMapper = mock(GroupMapper.class);
        groupService = new GroupServiceImpl(groupRepository, courseRepository, teacherService, groupMapper);
    }

    @Test
    void createGroup_success() {
        CreateGroupRequest request = mock(CreateGroupRequest.class);
        Course course = new Course();
        Teacher teacher = new Teacher();
        Group group = new Group();

        when(request.courseId()).thenReturn(1L);
        when(request.teacherId()).thenReturn(2L);
        when(request.name()).thenReturn("Group A");

        when(groupRepository.isGroupExistsInCourseByName("Group A", 1L)).thenReturn(false);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(teacherService.findTeacherById(2L)).thenReturn(teacher);
        when(groupMapper.mapCreateGroupRequestToCourse(request, course, teacher)).thenReturn(group);
        when(groupRepository.save(group)).thenReturn(group);

        Group result = groupService.createGroup(request);
        assertEquals(group, result);
    }

    @Test
    void unenrollChild_success() {
        EnrollmentActionDto dto = mock(EnrollmentActionDto.class);
        Group group = new Group();
        group.setCurrentParticipants(3);

        when(dto.groupId()).thenReturn(1L);
        when(dto.childId()).thenReturn(10L);
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        boolean result = groupService.unenrollChild(dto);

        assertTrue(result);
        verify(groupRepository).unenrollChildFromCourseGroup(10L, 1L);
        verify(groupRepository).save(group);
        assertEquals(2, group.getCurrentParticipants());
    }

    @Test
    void findGroupById_shouldReturnGroup() {
        Group group = new Group();
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        Optional<Group> result = groupService.findGroupById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void findGroupById_shouldReturnEmpty() {
        when(groupRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Group> result = groupService.findGroupById(999L);
        assertTrue(result.isEmpty());
    }
}
