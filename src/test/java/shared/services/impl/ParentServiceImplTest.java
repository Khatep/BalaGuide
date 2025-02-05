package shared.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import kz.balaguide.core.exceptions.buisnesslogic.financialoperation.heirs.InsufficientFundsException;
import kz.balaguide.core.entities.Child;
import kz.balaguide.core.entities.Course;
import kz.balaguide.core.entities.EducationCenter;
import kz.balaguide.core.entities.Parent;
import kz.balaguide.core.enums.CourseCategory;
import kz.balaguide.core.enums.Gender;
import kz.balaguide.core.repositories.child.ChildRepository;
import kz.balaguide.core.repositories.course.CourseRepository;
import kz.balaguide.core.repositories.educationcenter.EducationCenterRepository;
import kz.balaguide.core.repositories.parent.ParentRepository;
import kz.balaguide.services.course.CourseService;
import kz.balaguide.services.kafka.email.EmailProducerService;
import kz.balaguide.services.receipt.ReceiptService;
import kz.balaguide.services.parent.ParentServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParentServiceImplTest {

    @InjectMocks
    private ParentServiceImpl parentService;

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private ChildRepository childRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseService courseService;

    @Mock
    private EducationCenterRepository educationCenterRepository;

    @Mock
    private ReceiptService receiptService;

    @Mock
    private EmailProducerService emailProducerService;

    private Course course;
    private Parent parent;
    private Child child;
    private EducationCenter center;

    @BeforeEach
    void setUp() {
        center = EducationCenter.builder()
                .name("Learning Center")
                .dateOfCreated(LocalDate.of(2022, 1, 15))
                .phoneNumber("111-222")
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
                .durability(10)
                .maxParticipants(30)
                .currentParticipants(0)
                .educationCenter(center)
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
                .birthDate(LocalDate.of(2015, 6, 1))
                .phoneNumber("1234567891")
                .password("childpass1")
                .gender(Gender.FEMALE)
                .parent(parent)
                .build();
    }


    @Test
    void testAddChild() {
        when(parentRepository.findById(parent.getId())).thenReturn(Optional.of(parent));
        when(childRepository.save(any(Child.class))).thenReturn(child);

        Child result = parentService.addChild(parent.getId(), child);

        assertEquals(child, result);
        verify(childRepository).save(child);
    }

    @Test
    void testRemoveChildInvalidPassword() {
        when(childRepository.findById(child.getId())).thenReturn(Optional.of(child));
        when(parentRepository.findById(parent.getId())).thenReturn(Optional.of(parent));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                parentService.removeChild(parent.getId(), child.getId()));

        assertEquals("Incorrect parent password", exception.getMessage());
    }

    @Test
    void testRemoveChildNotFound() {
        when(childRepository.findById(child.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                parentService.removeChild(parent.getId(), child.getId()));

        assertEquals("Child not found", exception.getMessage());
    }

    @Test
    void testGetMyChildrenSuccess() {
        when(parentRepository.findById(parent.getId())).thenReturn(Optional.of(parent));
        when(childRepository.findAllByParentId(parent.getId())).thenReturn(List.of(child));

        List<Child> children = parentService.getMyChildren(parent.getId());

        assertEquals(1, children.size());
        assertEquals(child, children.get(0));
    }

    @Test
    void testGetMyChildrenWrongId() {
        when(parentRepository.findById(parent.getId())).thenReturn(Optional.of(parent));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                parentService.getMyChildren(parent.getId()));

        assertEquals("Parent with id: 9999 not found", exception.getMessage());
    }

    @Test
    void testRemoveChildSuccess() {
        when(childRepository.findById(child.getId())).thenReturn(Optional.of(child));
        when(parentRepository.findById(parent.getId())).thenReturn(Optional.of(parent));

        boolean result = parentService.removeChild(parent.getId(), child.getId());

        assertTrue(result);
        verify(childRepository).deleteById(child.getId());
    }

    @Test
    void testEnrollChildToCourseSuccess() {
        when(parentRepository.findById(parent.getId())).thenReturn(Optional.of(parent));
        when(childRepository.findById(child.getId())).thenReturn(Optional.of(child));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(courseService.enrollChild(course.getId(), child.getId())).thenReturn(true);

        parent.setBalance(BigDecimal.valueOf(100000));
        parentRepository.save(parent);

        boolean result = parentService.enrollChildToCourse(parent.getId(), child.getId(), course.getId());

        assertTrue(result);
        verify(courseService).enrollChild(course.getId(), child.getId());
    }

    @Test
    void testEnrollChildToCourseInsufficientFunds() {
        when(parentRepository.findById(parent.getId())).thenReturn(Optional.of(parent));
        when(childRepository.findById(child.getId())).thenReturn(Optional.of(child));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        parent.setBalance(BigDecimal.valueOf(30));

        Exception exception = assertThrows(InsufficientFundsException.class, () ->
                parentService.enrollChildToCourse(parent.getId(), child.getId(), course.getId()));

        assertEquals("Insufficient funds for payment", exception.getMessage());
    }

    @Test
    void testUnenrollChildFromCourseSuccess() {
        when(parentRepository.findById(parent.getId())).thenReturn(Optional.of(parent));
        when(childRepository.findById(child.getId())).thenReturn(Optional.of(child));
        when(courseRepository.isChildEnrolledInCourse(course.getId(), child.getId())).thenReturn(true);
        when(courseService.unenrollChild(course.getId(), child.getId())).thenReturn(true);

        boolean result = parentService.unenrollChildFromCourse(parent.getId(), child.getId(), course.getId());

        assertTrue(result);
        verify(courseService).unenrollChild(course.getId(), child.getId());
    }

    @Test
    void testUnenrollChildFromCourseNotEnrolled() {
        when(parentRepository.findById(parent.getId())).thenReturn(Optional.of(parent));
        when(childRepository.findById(child.getId())).thenReturn(Optional.of(child));
        when(courseRepository.isChildEnrolledInCourse(course.getId(), child.getId())).thenReturn(false);

        boolean result = parentService.unenrollChildFromCourse(parent.getId(), child.getId(), course.getId());

        assertFalse(result);
        verify(courseService, never()).unenrollChild(anyLong(), anyLong());
    }

    @Test
    void testPayForCourseSuccess() {
        when(parentRepository.findById(parent.getId())).thenReturn(Optional.of(parent));
        when(educationCenterRepository.save(center)).thenReturn(center);

        parent.setBalance(BigDecimal.valueOf(100));
        course.setPrice(BigDecimal.valueOf(50));

        boolean result = parentService.payForCourse(parent.getId(), course);

        assertTrue(result);
        assertEquals(BigDecimal.valueOf(50), parent.getBalance());
        verify(parentRepository).save(parent);
    }

    @Test
    void testPayForCourseInsufficientFunds() {
        when(parentRepository.findById(parent.getId())).thenReturn(Optional.of(parent));

        parent.setBalance(BigDecimal.valueOf(30));
        course.setPrice(BigDecimal.valueOf(50));

        Exception exception = assertThrows(InsufficientFundsException.class, () ->
                parentService.payForCourse(parent.getId(), course));

        assertEquals("Insufficient funds for payment", exception.getMessage());
        verify(parentRepository, never()).save(parent);
    }

    @Test
    void testAddBalanceSuccess() {
        when(parentRepository.findById(parent.getId())).thenReturn(Optional.of(parent));

        String result = parentService.addBalance(parent.getId(), 50, "99955562515313");

        assertEquals("Balance updated successfully. New balance: " + BigDecimal.valueOf(150), result);
        assertEquals(BigDecimal.valueOf(150), parent.getBalance());
        verify(parentRepository).save(parent);
    }

}
