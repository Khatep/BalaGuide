package shared;

import kz.balaguide.common_module.core.entities.*;
import kz.balaguide.common_module.core.enums.PaymentMethod;
import kz.balaguide.common_module.core.enums.PaymentStatus;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.financialoperation.heirs.BalanceUpdateException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.financialoperation.heirs.InsufficientFundsException;
import kz.balaguide.education_center_module.repository.EducationCenterRepository;
import kz.balaguide.message_module.email.kafka.EmailProducerService;
import kz.balaguide.parent_module.repository.ParentRepository;
import kz.balaguide.payment_module.repository.PaymentRepository;
import kz.balaguide.payment_module.services.payment.PaymentServiceImpl;
import kz.balaguide.payment_module.services.receipt.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    private ParentRepository parentRepository;
    private ReceiptService receiptService;
    private EducationCenterRepository educationCenterRepository;
    private EmailProducerService emailProducerService;
    private PaymentRepository paymentRepository;

    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        parentRepository = mock(ParentRepository.class);
        receiptService = mock(ReceiptService.class);
        educationCenterRepository = mock(EducationCenterRepository.class);
        emailProducerService = mock(EmailProducerService.class);
        paymentRepository = mock(PaymentRepository.class);

        paymentService = new PaymentServiceImpl(
                parentRepository, receiptService, educationCenterRepository,
                emailProducerService, paymentRepository
        );
    }

    @Test
    void payForCourse_success() {
        Parent parent = new Parent();
        parent.setBalance(new BigDecimal("1000.00"));

        EducationCenter center = new EducationCenter();
        center.setBalance(new BigDecimal("0"));

        Course course = new Course();
        course.setPrice(new BigDecimal("500.00"));
        course.setEducationCenter(center);

        Child child = new Child();

        when(parentRepository.save(parent)).thenReturn(parent);
        when(educationCenterRepository.save(center)).thenReturn(center);
        when(paymentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(receiptService.createReceipt(any())).thenReturn(new Receipt());

        boolean result = paymentService.payForCourse(parent, child, course);

        assertTrue(result);
        assertEquals(new BigDecimal("500.00"), parent.getBalance());
        assertEquals(new BigDecimal("500.00"), center.getBalance());

        verify(emailProducerService).sendReceiptToTopic(any());
    }

    @Test
    void payForCourse_insufficientFunds_shouldThrowException() {
        Parent parent = new Parent();
        parent.setBalance(new BigDecimal("100"));

        Course course = new Course();
        course.setPrice(new BigDecimal("200"));

        Child child = new Child();

        assertThrows(InsufficientFundsException.class,
                () -> paymentService.payForCourse(parent, child, course));
    }

    @Test
    void payForCourse_educationCenterSaveFails_shouldRollbackAndThrow() {
        Parent parent = new Parent();
        parent.setId(1L);
        parent.setBalance(new BigDecimal("500"));

        EducationCenter center = new EducationCenter();
        center.setBalance(new BigDecimal("0"));

        Course course = new Course();
        course.setPrice(new BigDecimal("500"));
        course.setEducationCenter(center);

        Child child = new Child();

        when(parentRepository.save(any())).thenReturn(parent);
        doThrow(new RuntimeException("save failed")).when(educationCenterRepository).save(center);

        assertThrows(BalanceUpdateException.class,
                () -> paymentService.payForCourse(parent, child, course));

        assertEquals(new BigDecimal("500"), parent.getBalance()); // rollback должен вернуть деньги
    }
}
