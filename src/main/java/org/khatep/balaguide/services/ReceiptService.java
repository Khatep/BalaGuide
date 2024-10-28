package org.khatep.balaguide.services;

import org.khatep.balaguide.models.entities.Receipt;

public interface ReceiptService {

    /**
     * Creates a new {@link Receipt} for a given parent and course.
     *
     * <p>The receipt includes details such as the parent ID, course ID, VAT percentage, creation date, and payment method.
     *
     * @param parentId the ID of the parent making the payment
     * @param courseId the ID of the course for which payment is made
     * @return a {@link Receipt} object containing the payment details
     */
    Receipt createReceipt(Long parentId, Long courseId);
}
