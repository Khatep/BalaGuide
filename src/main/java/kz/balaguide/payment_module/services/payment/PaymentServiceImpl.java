package kz.balaguide.payment_module.services.payment;

import kz.balaguide.common_module.core.annotations.ForLog;
import kz.balaguide.common_module.core.entities.*;
import kz.balaguide.common_module.core.enums.PaymentMethod;
import kz.balaguide.common_module.core.enums.PaymentStatus;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.financialoperation.heirs.BalanceUpdateException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.financialoperation.heirs.InsufficientFundsException;
import kz.balaguide.education_center_module.repository.EducationCenterRepository;
import kz.balaguide.notification_module.services.kafka.email.EmailProducerService;
import kz.balaguide.parent_module.repository.ParentRepository;
import kz.balaguide.payment_module.repository.PaymentRepository;
import kz.balaguide.payment_module.services.receipt.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final ParentRepository parentRepository;
    private final ReceiptService receiptService;
    private final EducationCenterRepository educationCenterRepository;
    private final EmailProducerService emailProducerService;
    private final PaymentRepository paymentRepository;

    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public boolean payForCourse(Parent parent, Child child, Course course) {

        BigDecimal coursePrice = course.getPrice();

        if (parent.getBalance().compareTo(coursePrice) < 0) {
            throw new InsufficientFundsException("Insufficient funds for payment");
        }

        parent.setBalance(parent.getBalance().subtract(coursePrice));
        parentRepository.save(parent);

        try {
            course.getEducationCenter().setBalance(course.getEducationCenter().getBalance().add(coursePrice));
            educationCenterRepository.save(course.getEducationCenter());
        } catch (Exception e) {
            parent.setBalance(parent.getBalance().add(coursePrice));
            parentRepository.save(parent);
            throw new BalanceUpdateException(
                    "Failed to update balance for Education Center after parent ID: "
                            + parent.getId()
                            + " payment, transaction rolled back"
            );
        }

        Payment payment = Payment.builder()
                .parent(parent)
                .child(child)
                .course(course)
                .amount(coursePrice)
                .paymentTime(LocalDateTime.now())
                .percentOfVat(12)
                .paymentMethod(PaymentMethod.BANK_CARD)
                .paymentStatus(PaymentStatus.PAID)
                .transactionId(String.valueOf(UUID.randomUUID()))
                .build();

        paymentRepository.save(payment);

        Receipt receipt = receiptService.createReceipt(payment);

        emailProducerService.sendReceiptToTopic(receipt);
        return true;
    }
}
