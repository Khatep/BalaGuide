package shared;

import kz.balaguide.auth_module.services.AuthUserService;
import kz.balaguide.common_module.core.entities.AuthUser;
import kz.balaguide.common_module.core.entities.Teacher;
import kz.balaguide.common_module.core.enums.Gender;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.teacher_module.dto.CreateTeacherRequest;
import kz.balaguide.teacher_module.mappers.TeacherMapper;
import kz.balaguide.teacher_module.repositories.TeacherRepository;
import kz.balaguide.teacher_module.services.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherServiceImplTest {

    private TeacherRepository teacherRepository;
    private TeacherMapper teacherMapper;
    private AuthUserService authUserService;
    private TeacherServiceImpl teacherService;

    @BeforeEach
    void setUp() {
        teacherRepository = mock(TeacherRepository.class);
        teacherMapper = mock(TeacherMapper.class);
        authUserService = mock(AuthUserService.class);

        teacherService = new TeacherServiceImpl(teacherRepository, teacherMapper, authUserService);
    }

    @Test
    void createTeacher_success() {
        CreateTeacherRequest request = new CreateTeacherRequest(
                "Айгүл",
                "Жумабаева",
                LocalDate.of(1985, 3, 14),                    // birthDate
                "+77019998877",                               // phoneNumber
                "a.zhumbayeva@example.com",                  // email
                new BigDecimal("280000"),                    // salary
                Gender.FEMALE                                 // enum Gender
        );

        Teacher teacher = new Teacher();
        when(teacherRepository.existsByEmail(request.email())).thenReturn(false);
        when(teacherRepository.existsByPhoneNumber(request.phoneNumber())).thenReturn(false);
        when(teacherMapper.mapCreateTeacherRequestToTeacher(request)).thenReturn(teacher);

        AuthUser authUser = new AuthUser();
        UserDetails userDetails = authUser;
        when(authUserService.userDetailsService().loadUserByUsername(request.phoneNumber()))
                .thenReturn(userDetails);

        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher result = teacherService.createTeacher(request);

        assertNotNull(result);
        verify(teacherRepository).save(teacher);
        verify(teacherMapper).mapCreateTeacherRequestToTeacher(request);
    }

    @Test
    void createTeacher_duplicateEmail_throwsException() {
        CreateTeacherRequest request = new CreateTeacherRequest(
                "Айгүл",
                "Жумабаева",
                LocalDate.of(1985, 3, 14),
                "+77019998877",
                "a.zhumbayeva@example.com",
                new BigDecimal("280000"),
                Gender.FEMALE
        );

        when(teacherRepository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> teacherService.createTeacher(request));
        verify(teacherRepository, never()).save(any());
    }

    @Test
    void createTeacher_duplicatePhone_throwsException() {
        CreateTeacherRequest request = new CreateTeacherRequest(
                "Айгүл",
                "Жумабаева",
                LocalDate.of(1985, 3, 14),
                "+77019998877",
                "a.zhumbayeva@example.com",
                new BigDecimal("280000"),
                Gender.FEMALE
        );

        when(teacherRepository.existsByEmail(request.email())).thenReturn(false);
        when(teacherRepository.existsByPhoneNumber(request.phoneNumber())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> teacherService.createTeacher(request));
        verify(teacherRepository, never()).save(any());
    }

    @Test
    void findTeacherById_success() {
        Teacher teacher = new Teacher();
        when(teacherRepository.findById(1L)).thenReturn(java.util.Optional.of(teacher));

        Teacher result = teacherService.findTeacherById(1L);
        assertNotNull(result);
        assertEquals(teacher, result);
    }

    @Test
    void findTeacherById_notFound() {
        when(teacherRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.findTeacherById(99L));
    }
}

