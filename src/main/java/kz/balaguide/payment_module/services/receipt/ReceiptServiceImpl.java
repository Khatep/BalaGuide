package kz.balaguide.payment_module.services.receipt;

import lombok.RequiredArgsConstructor;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.Parent;
import kz.balaguide.common_module.core.entities.Receipt;
import kz.balaguide.common_module.core.enums.PaymentMethod;
import kz.balaguide.course_module.repository.CourseRepository;
import kz.balaguide.parent_module.repository.ParentRepository;
import kz.balaguide.payment_module.repository.ReceiptRepository;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new ParentNotFoundException("Parent with id: " + parentId + " not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + courseId + " not found"));

        Receipt receipt = Receipt.builder()
                .parentId(parent.getId())
                .courseId(course.getId())
                .percentOfVat(12)
                .paymentMethod(PaymentMethod.BANK_CARD)
                .build();
        return receiptRepository.save(receipt);
    }
}
