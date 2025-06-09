package shared;

import kz.balaguide.auth_module.services.AuthUserService;
import kz.balaguide.child_module.mappers.ChildMapper;
import kz.balaguide.child_module.repository.ChildRepository;
import kz.balaguide.common_module.core.entities.*;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.course_module.repository.CourseRepository;
import kz.balaguide.course_module.repository.GroupRepository;
import kz.balaguide.course_module.services.CourseService;
import kz.balaguide.course_module.services.GroupService;
import kz.balaguide.parent_module.dtos.CreateChildRequest;
import kz.balaguide.parent_module.dtos.CreateParentRequest;
import kz.balaguide.parent_module.dtos.UpdateParentRequest;
import kz.balaguide.parent_module.mappers.ParentMapper;
import kz.balaguide.parent_module.repository.ParentRepository;
import kz.balaguide.parent_module.services.ParentServiceImpl;
import kz.balaguide.payment_module.services.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParentServiceImplTest {

    private ParentRepository parentRepository;
    private ChildRepository childRepository;
    private CourseRepository courseRepository;
    private GroupRepository groupRepository;
    private CourseService courseService;
    private GroupService groupService;
    private PaymentService paymentService;
    private AuthUserService authUserService;
    private ParentMapper parentMapper;
    private ChildMapper childMapper;
    private ParentServiceImpl parentService;

    @BeforeEach
    void setUp() {
        parentRepository = mock(ParentRepository.class);
        childRepository = mock(ChildRepository.class);
        courseRepository = mock(CourseRepository.class);
        groupRepository = mock(GroupRepository.class);
        courseService = mock(CourseService.class);
        groupService = mock(GroupService.class);
        paymentService = mock(PaymentService.class);
        authUserService = mock(AuthUserService.class);
        parentMapper = mock(ParentMapper.class);
        childMapper = mock(ChildMapper.class);

        parentService = new ParentServiceImpl(
                parentRepository,
                childRepository,
                courseRepository,
                groupRepository,
                courseService,
                groupService,
                paymentService,
                authUserService,
                parentMapper,
                childMapper
        );
    }

    @Test
    void createParentAndSave_success() {
        CreateParentRequest request = mock(CreateParentRequest.class);
        when(request.email()).thenReturn("test@mail.com");
        when(request.phoneNumber()).thenReturn("123456");

        when(parentRepository.existsByEmail("test@mail.com")).thenReturn(false);
        when(parentRepository.existsByPhoneNumber("123456")).thenReturn(false);

        AuthUser authUser = new AuthUser();
        when(authUserService.userDetailsService().loadUserByUsername("123456")).thenReturn(authUser);

        Parent parent = new Parent();
        when(parentMapper.mapCreateParentRequestToParent(request)).thenReturn(parent);
        when(parentRepository.save(any())).thenReturn(parent);

        Parent saved = parentService.createParentAndSave(request);
        assertEquals(parent, saved);
    }

    @Test
    void findByPhoneNumber_shouldReturnParent() {
        Parent parent = new Parent();
        when(parentRepository.findByPhoneNumber("123")).thenReturn(Optional.of(parent));
        assertEquals(parent, parentService.findByPhoneNumber("123"));
    }

    @Test
    void findByPhoneNumber_shouldThrow() {
        when(parentRepository.findByPhoneNumber("123")).thenReturn(Optional.empty());
        assertThrows(ParentNotFoundException.class, () -> parentService.findByPhoneNumber("123"));
    }

    @Test
    void findParentById_success() {
        Parent parent = new Parent();
        when(parentRepository.findById(1L)).thenReturn(Optional.of(parent));
        assertEquals(parent, parentService.findParentById(1L));
    }

    @Test
    void updateParent_success() {
        UpdateParentRequest request = mock(UpdateParentRequest.class);
        Parent existing = new Parent();
        existing.setId(1L);

        when(request.getFirstName()).thenReturn("Ali");
        when(request.getLastName()).thenReturn("Utepov");
        when(request.getAddress()).thenReturn("Test street");
        when(request.getBalance()).thenReturn(BigDecimal.valueOf(1000));
        when(request.getEmail()).thenReturn("ali@mail.com");
        when(request.getPhoneNumber()).thenReturn("123456");

        when(parentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(parentRepository.save(existing)).thenReturn(existing);

        Parent updated = parentService.updateParent(1L, request);
        assertEquals("Ali", updated.getFirstName());
    }

    @Test
    void addBalance_success() {
        Parent parent = new Parent();
        parent.setBalance(BigDecimal.valueOf(100));
        when(parentRepository.findById(1L)).thenReturn(Optional.of(parent));

        BankCard card = new BankCard();
        String result = parentService.addBalance(1L, 200, card);
        assertTrue(result.contains("Balance updated"));
    }

    @Test
    void getMyChildren_success() {
        Parent parent = new Parent();
        when(parentRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(childRepository.findAllByParentId(1L)).thenReturn(List.of(new Child(), new Child()));

        List<Child> children = parentService.getMyChildren(1L);
        assertEquals(2, children.size());
    }

    @Test
    void removeChild_shouldDelete() {
        Parent parent = new Parent();
        parent.setId(1L);

        Child child = new Child();
        child.setId(2L);
        child.setParent(parent);

        when(childRepository.findById(2L)).thenReturn(Optional.of(child));
        when(parentRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(childRepository.existsById(2L)).thenReturn(false);

        boolean result = parentService.removeChild(1L, 2L);
        assertTrue(result);
    }
}
