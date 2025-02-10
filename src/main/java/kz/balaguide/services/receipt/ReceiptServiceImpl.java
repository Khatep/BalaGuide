package kz.balaguide.services.receipt;

import lombok.RequiredArgsConstructor;
import kz.balaguide.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.core.entities.Course;
import kz.balaguide.core.entities.Parent;
import kz.balaguide.core.entities.Receipt;
import kz.balaguide.core.enums.PaymentMethod;
import kz.balaguide.core.repositories.course.CourseRepository;
import kz.balaguide.core.repositories.parent.ParentRepository;
import kz.balaguide.core.repositories.receipt.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ParentRepository parentRepository;
    private final CourseRepository courseRepository;

    /**
     * Creates a new {@link Receipt} for a given parent and course.
     *
     * <p>The receipt includes details such as the parent ID, course ID, VAT percentage, creation date, and payment method.
     *
     * @param parentId the ID of the parent making the payment
     * @param courseId the ID of the course for which payment is made
     * @return a {@link Receipt} object containing the payment details
     */
    @Override
    public Receipt createReceipt(Long parentId, Long courseId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id " + parentId + " not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + courseId + " not found"));

        Receipt receipt = Receipt.builder()
                .parentId(parent.getId())
                .courseId(course.getId())
                .percentOfVat(12)
                .paymentMethod(PaymentMethod.BANK_CARD)
                .build();
        return receiptRepository.save(receipt);
    }
}
