package shared.services.impl;

import kz.balaguide.core.enums.CourseCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import kz.balaguide.core.exceptions.buisnesslogic.generic.CourseFullException;
import kz.balaguide.core.exceptions.buisnesslogic.generic.IneligibleChildException;
import kz.balaguide.utils.mappers.CourseMapper;
import kz.balaguide.core.entities.Child;
import kz.balaguide.core.entities.Course;
import kz.balaguide.core.entities.EducationCenter;
import kz.balaguide.core.entities.Parent;
import kz.balaguide.core.enums.Gender;
import kz.balaguide.core.dtos.requests.CourseRequest;
import kz.balaguide.core.repositories.child.ChildRepository;
import kz.balaguide.core.repositories.course.CourseRepository;
import kz.balaguide.services.course.CourseServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ChildRepository childRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseMapper courseMapper;

    private Course course;
    private Child child;
    private Parent parent;
    private EducationCenter educationCenter;

    @BeforeEach
    void setUp() {
        educationCenter = EducationCenter.builder()
                .id(100L)
                .name("Learning Center")
                .dateOfCreated(LocalDate.of(2022, 1, 15))
                .phoneNumber("111-222")
                .email("center@gmail.com")
                .password("password12345")
                .address("Abai 12")
                .instagramLink("https://instagram.com/learning")
                .balance(BigDecimal.valueOf(2000.00))
                .build();

        course = Course.builder()
                .id(100L)
                .name("Java Course")
                .description("Java core and 1.8")
                .courseCategory(CourseCategory.PROGRAMMING)
                .ageRange("6-16")
                .price(BigDecimal.valueOf(50.00))
                .durability(0)
                .maxParticipants(10)
                .currentParticipants(0)
                .build();

        parent = Parent.builder()
                .id(100L)
                .firstName("Nurgali")
                .lastName("Khatep")
                .phoneNumber("123456789")
                .password("password")
                .balance(BigDecimal.valueOf(100))
                .build();

        child = Child.builder()
                .id(100L)
                .firstName("Ergali")
                .lastName("Khatep")
                .birthDate(LocalDate.of(2017, 1, 1))
                .phoneNumber("1234567891")
                .password("childpass1")
                .gender(Gender.FEMALE)
                .parent(parent)
                .build();
    }

    @Test
    void testAddCourse() {
        when(courseRepository.save(course)).thenReturn(course);

        CourseRequest courseRequest = courseMapper.mapCourseToCourseRequest(course);

        Course result = courseService.addCourse(courseRequest);

        assertNotNull(result);
        assertEquals(course, result);
        verify(courseRepository).save(course);
    }

    @Test
    void testUpdateInformation() {
        Course updatedCourse = new Course();
        updatedCourse.setName("Updated Course");

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseService.updateInformation(course.getId(), updatedCourse);

        assertNotNull(result);
        assertEquals(course, result);
        verify(courseRepository).findById(course.getId());
        verify(courseRepository).save(course);
    }

    @Test
    void testEnrollChild() {
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(childRepository.findById(child.getId())).thenReturn(Optional.of(child));

        boolean result = courseService.enrollChild(course.getId(), child.getId());

        assertTrue(result);
        verify(courseRepository).enrollChildInCourse(child.getId(), course.getId());
        assertEquals(1, course.getCurrentParticipants());
    }

    @Test
    void testEnrollChildCourseFull() {
        course.setCurrentParticipants(course.getMaxParticipants());

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(childRepository.findById(child.getId())).thenReturn(Optional.of(child));

        assertThrows(CourseFullException.class, () -> courseService.enrollChild(course.getId(), child.getId()));
    }

    @Test
    void testEnrollChildIneligible() {
        child.setBirthDate(LocalDate.of(1999, 1, 1));

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(childRepository.findById(child.getId())).thenReturn(Optional.of(child));

        assertThrows(IneligibleChildException.class, () -> courseService.enrollChild(course.getId(), child.getId()));
    }

    @Test
    void testUnenrollChild() {
        course.setCurrentParticipants(1);
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(childRepository.findById(child.getId())).thenReturn(Optional.of(child));

        boolean result = courseService.unenrollChild(course.getId(), child.getId());

        assertTrue(result);
        assertEquals(0, course.getCurrentParticipants());
    }

    @Test
    void testUnenrollChildNotEnrolled() {
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(childRepository.findById(child.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                courseService.unenrollChild(course.getId(), child.getId()));

        assertEquals("Child not found with id: " + child.getId(), exception.getMessage());
        verify(courseRepository).findById(course.getId());
    }

    @Test
    void testIsCourseFull() {
        course.setCurrentParticipants(course.getMaxParticipants());
        boolean result = courseService.isCourseFull(course);
        assertTrue(result);
    }

    @Test
    void testGetCurrentParticipants() {
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        int currentParticipants = courseService.getCurrentParticipants(course);
        assertEquals(0, currentParticipants);
    }

    @Test
    void testIsChildEligible() {
        boolean result = courseService.isChildEligible(course, child);
        assertTrue(result);
    }
}
