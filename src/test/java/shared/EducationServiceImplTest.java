package shared;

import kz.balaguide.auth_module.services.AuthUserService;
import kz.balaguide.common_module.core.entities.AuthUser;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.education_center_module.dtos.*;
import kz.balaguide.education_center_module.mappers.EducationCenterMapper;
import kz.balaguide.education_center_module.repository.EducationCenterRepository;
import kz.balaguide.education_center_module.services.EducationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EducationServiceImplTest {

    private EducationCenterRepository educationCenterRepository;
    private AuthUserService authUserService;
    private EducationCenterMapper educationCenterMapper;

    private EducationServiceImpl educationService;

    @BeforeEach
    void setup() {
        educationCenterRepository = mock(EducationCenterRepository.class);
        authUserService = mock(AuthUserService.class);
        educationCenterMapper = mock(EducationCenterMapper.class);

        educationService = new EducationServiceImpl(authUserService, educationCenterRepository, educationCenterMapper);
    }

    @Test
    void createEducationCenter_success() {
        EducationCenterCreateReq req = new EducationCenterCreateReq(
                "Test Center",                                 // name
                LocalDate.of(2012, 1, 1),                      // dateOfCreated
                "+87771112233",                                // phoneNumber
                "test@mail.com",                               // email
                "Astana, Abay Street",                         // address
                "https://instagram.com/testcenter"             // instagramLink
        );

        when(educationCenterRepository.existsByEmail(req.email())).thenReturn(false);
        when(educationCenterRepository.existsByPhoneNumber(req.phoneNumber())).thenReturn(false);

        EducationCenter educationCenter = new EducationCenter();
        when(educationCenterMapper.mapEducationCenterCreateReqToEducationCenter(req)).thenReturn(educationCenter);

        AuthUser mockUser = new AuthUser();
        when(authUserService.userDetailsService()).thenReturn(username -> mockUser);

        when(educationCenterRepository.save(any())).thenReturn(educationCenter);

        EducationCenter result = educationService.createEducationCenter(req);
        assertNotNull(result);
        verify(educationCenterRepository).save(educationCenter);
    }


    @Test
    void createEducationCenter_duplicatePhone() {
        EducationCenterCreateReq req = new EducationCenterCreateReq(
                "Test Center",
                LocalDate.of(2012, 1, 1),
                "+87771112233",
                "test@mail.com",
                "Astana, Abay Street",
                "https://instagram.com/testcenter"
        );

        when(educationCenterRepository.existsByEmail(req.email())).thenReturn(false);
        when(educationCenterRepository.existsByPhoneNumber(req.phoneNumber())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> educationService.createEducationCenter(req));
    }


    @Test
    void calculateTotalRevenue_returnsCorrectValue() {
        when(educationCenterRepository.calculateTotalRevenue(1L)).thenReturn(12000.0);
        Double result = educationService.calculateTotalRevenue(1L);
        assertEquals(12000.0, result);
    }

    @Test
    void getMonthlyChildrenGrowthFake_shouldReturnFakeGrowth() {
        Map<String, Object> month1 = Map.of("month", "2024-01");
        Map<String, Object> month2 = Map.of("month", "2024-02");

        when(educationCenterRepository.getMonthlyChildrenGrowth(1L)).thenReturn(List.of(month1, month2));

        List<MonthlyChildrenGrowthDTO> result = educationService.getMonthlyChildrenGrowthFake(1L);
        assertEquals(2, result.size());
    }

    @Test
    void getCoursesByEducationCenter_shouldReturnList() {
        List<Course> courses = List.of(new Course(), new Course());
        when(educationCenterRepository.getAllCoursesByEducationalCenterId(1L)).thenReturn(courses);

        List<Course> result = educationService.getCoursesByEducationCenter(1L);
        assertEquals(2, result.size());
    }
}
