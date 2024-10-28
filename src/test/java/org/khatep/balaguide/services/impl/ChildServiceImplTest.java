package org.khatep.balaguide.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.entities.Parent;
import org.khatep.balaguide.models.enums.Category;
import org.khatep.balaguide.models.enums.Gender;
import org.khatep.balaguide.repositories.ChildRepository;
import org.khatep.balaguide.repositories.CourseRepository;
import org.khatep.balaguide.repositories.ParentRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChildServiceImplTest {

    @InjectMocks
    private ChildServiceImpl childService;

    @Mock
    private ChildRepository childRepository;

    @Mock
    private CourseRepository courseRepository;

    private Child child;

    private List<Course> courses;

    @BeforeEach
    void setUp() {
        Course course = Course.builder()
                .id(100L)
                .name("Java Course")
                .description("Java core and 1.8")
                .category(Category.PROGRAMMING)
                .ageRange("6-16")
                .price(BigDecimal.valueOf(50.00))
                .durability(10)
                .address("123 Learning Rd")
                .maxParticipants(30)
                .currentParticipants(0)
                .build();
        courses = List.of(course);

        Parent parent = Parent.builder()
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
                .birthDate(LocalDate.of(2015, 6, 1))
                .phoneNumber("1234567891")
                .password("childpass1")
                .gender(Gender.FEMALE)
                .parent(parent)
                .coursesEnrolled(List.of(courses.get(0)))
                .build();
    }

    @Test
    void testGetMyCoursesSuccess() {
        when(childRepository.findById(child.getId())).thenReturn(Optional.of(child));
        when(courseRepository.findAllByChildId(child.getId())).thenReturn(courses);

        List<Course> result = childService.getMyCourses(child);

        assertEquals(courses, result);
        verify(childRepository).findById(child.getId());
        verify(courseRepository).findAllByChildId(child.getId());
    }

    @Test
    void testGetMyCoursesChildNotFound() {
        when(childRepository.findById(child.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                childService.getMyCourses(child));

        assertEquals("Child not found with ID: " + child.getId(), exception.getMessage());
        verify(childRepository).findById(child.getId());
        verify(courseRepository, never()).findAllByChildId(anyLong());
    }

    @Test
    void testLoginSuccess() {
        when(childRepository.findByPhoneNumber(child.getPhoneNumber())).thenReturn(Optional.of(child));

        boolean result = childService.login(child);

        assertTrue(result);
        verify(childRepository).findByPhoneNumber(child.getPhoneNumber());
    }

    @Test
    void testLoginChildNotFound() {
        when(childRepository.findByPhoneNumber(child.getPhoneNumber())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                childService.login(child));

        assertEquals("Child not found with phone number: " + child.getPhoneNumber(), exception.getMessage());
        verify(childRepository).findByPhoneNumber(child.getPhoneNumber());
    }

    @Test
    void testLoginWrongPassword() {
        Child childFromDb = Child.builder()
                .id(1L)
                .phoneNumber(child.getPhoneNumber())
                .password("wrongPassword")
                .build();

        when(childRepository.findByPhoneNumber(child.getPhoneNumber())).thenReturn(Optional.of(childFromDb));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                childService.login(child));

        assertEquals("Wrong child password with ID: " + child.getId(), exception.getMessage());
        verify(childRepository).findByPhoneNumber(child.getPhoneNumber());
    }
}
