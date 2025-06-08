
package shared;

import kz.balaguide.child_module.repository.ChildRepository;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.common_module.core.entities.Group;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.CourseFullException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.IneligibleChildException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.EducationCenterNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.GroupNotFoundException;
import kz.balaguide.course_module.dto.CreateCourseRequest;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import kz.balaguide.course_module.mappers.CourseMapper;
import kz.balaguide.course_module.repository.CourseRepository;
import kz.balaguide.course_module.repository.GroupRepository;
import kz.balaguide.course_module.services.CourseServiceImpl;
import kz.balaguide.education_center_module.repository.EducationCenterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceImplTest {

    private CourseRepository courseRepository;
    private ChildRepository childRepository;
    private EducationCenterRepository educationCenterRepository;
    private GroupRepository groupRepository;
    private CourseMapper courseMapper;

    private CourseServiceImpl courseService;

    @BeforeEach
    void setup() {
        courseRepository = mock(CourseRepository.class);
        childRepository = mock(ChildRepository.class);
        educationCenterRepository = mock(EducationCenterRepository.class);
        groupRepository = mock(GroupRepository.class);
        courseMapper = mock(CourseMapper.class);

        courseService = new CourseServiceImpl(
                courseRepository, childRepository,
                educationCenterRepository, groupRepository,
                courseMapper
        );
    }

    @Test
    void createAndSaveCourse_success() {
        CreateCourseRequest request = mock(CreateCourseRequest.class);
        EducationCenter center = new EducationCenter();
        Course course = new Course();

        when(request.educationCenterId()).thenReturn(1L);
        when(educationCenterRepository.findById(1L)).thenReturn(Optional.of(center));
        when(courseMapper.mapCourseRequestToCourse(request, center)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.createAndSaveCourse(request);
        assertEquals(course, result);
    }

    @Test
    void createAndSaveCourse_educationCenterNotFound() {
        CreateCourseRequest request = mock(CreateCourseRequest.class);
        when(request.educationCenterId()).thenReturn(42L);
        when(educationCenterRepository.findById(42L)).thenReturn(Optional.empty());

        assertThrows(EducationCenterNotFoundException.class,
                () -> courseService.createAndSaveCourse(request));
    }

    @Test
    void updateInformation_success() {
        Course existing = new Course();
        Course updated = new Course();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(existing));
        doNothing().when(courseMapper).mapUpdatedCourseToExisting(existing, updated);
        when(courseRepository.save(existing)).thenReturn(existing);

        Course result = courseService.updateInformation(1L, updated);
        assertEquals(existing, result);
    }

    @Test
    void updateInformation_courseNotFound() {
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class,
                () -> courseService.updateInformation(999L, new Course()));
    }

    @Test
    void getCourses_shouldReturnList() {
        List<Course> mockCourses = List.of(new Course(), new Course());
        when(courseRepository.findAll()).thenReturn(mockCourses);
        assertEquals(2, courseService.getCourses().size());
    }

    @Test
    void enrollChild_success() {
        Group group = new Group();
        group.setGroupFull(false);
        group.setCurrentParticipants(1);

        Child child = new Child();
        child.setBirthDate(LocalDate.now().minusYears(10));
        child.setGroupsEnrolled(new ArrayList<>());


        Course course = new Course();
        course.setAgeRange("8-12");

        EnrollmentActionDto dto = mock(EnrollmentActionDto.class);
        when(dto.groupId()).thenReturn(1L);

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        boolean result = courseService.enrollChild(course, child, dto);
        assertTrue(result);
        assertEquals(2, group.getCurrentParticipants());
    }

    @Test
    void enrollChild_groupNotFound() {
        EnrollmentActionDto dto = mock(EnrollmentActionDto.class);
        when(dto.groupId()).thenReturn(404L);
        when(groupRepository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class,
                () -> courseService.enrollChild(new Course(), new Child(), dto));
    }

    @Test
    void enrollChild_groupIsFull() {
        Group group = new Group();
        group.setGroupFull(true);

        EnrollmentActionDto dto = mock(EnrollmentActionDto.class);
        when(dto.groupId()).thenReturn(1L);
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        assertThrows(CourseFullException.class,
                () -> courseService.enrollChild(new Course(), new Child(), dto));
    }
}
