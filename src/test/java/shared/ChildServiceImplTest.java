
package shared;

import kz.balaguide.child_module.repository.ChildRepository;
import kz.balaguide.child_module.services.ChildServiceImpl;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.Parent;
import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildrenNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import kz.balaguide.course_module.repository.GroupRepository;
import kz.balaguide.course_module.services.CourseService;
import kz.balaguide.course_module.services.GroupService;
import kz.balaguide.parent_module.services.ParentService;
import kz.balaguide.payment_module.services.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChildServiceImplTest {

    private ResponseMetadataService responseMetadataService;
    private ParentService parentService;
    private PaymentService paymentService;
    private CourseService courseService;
    private GroupService groupService;
    private ChildRepository childRepository;
    private GroupRepository groupRepository;

    private ChildServiceImpl childService;

    @BeforeEach
    void setUp() {
        responseMetadataService = mock(ResponseMetadataService.class);
        parentService = mock(ParentService.class);
        paymentService = mock(PaymentService.class);
        courseService = mock(CourseService.class);
        groupService = mock(GroupService.class);
        childRepository = mock(ChildRepository.class);
        groupRepository = mock(GroupRepository.class);

        childService = new ChildServiceImpl(
            responseMetadataService, parentService, paymentService,
            courseService, groupService, childRepository, groupRepository
        );
    }

    @Test
    void findAll_success() {
        List<Child> children = List.of(new Child(), new Child());
        Page<Child> page = new PageImpl<>(children);
        when(childRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        Page<Child> result = childService.findAll(0, 10);
        assertEquals(2, result.getTotalElements());
    }
    @Test
    void findAll_shouldThrowChildrenNotFound() {
        Page<Child> empty = Page.empty();
        when(childRepository.findAll(PageRequest.of(0, 10))).thenReturn(empty);

        ResponseMetadata mockMetadata = mock(ResponseMetadata.class);
        when(mockMetadata.getMessage()).thenReturn("No children found");
        when(responseMetadataService.findByCode(ResponseCode._0101)).thenReturn(mockMetadata);

        assertThrows(ChildrenNotFoundException.class, () -> childService.findAll(0, 10));
    }


    @Test
    void findById_success() {
        Child child = new Child();
        when(childRepository.findById(1L)).thenReturn(Optional.of(child));

        Child result = childService.findById(1L);
        assertEquals(child, result);
    }

    @Test
    void findById_notFound() {
        when(childRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(ChildNotFoundException.class, () -> childService.findById(999L));
    }

    // Остальные тесты можно добавить аналогично: enroll, unenroll, delete, getByParentId

}
