package org.khatep.balaguide.services.impl;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.exceptions.CourseNotFoundException;
import org.khatep.balaguide.exceptions.ParentNotFoundException;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.entities.Parent;
import org.khatep.balaguide.models.entities.Receipt;
import org.khatep.balaguide.models.enums.PaymentMethod;
import org.khatep.balaguide.repositories.CourseRepository;
import org.khatep.balaguide.repositories.ParentRepository;
import org.khatep.balaguide.repositories.ReceiptRepository;
import org.khatep.balaguide.services.ReceiptService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
                .dateOfCreated(LocalDate.now())
                .paymentMethod(PaymentMethod.BANK_CARD)
                .build();
        return receiptRepository.save(receipt);
    }
}
