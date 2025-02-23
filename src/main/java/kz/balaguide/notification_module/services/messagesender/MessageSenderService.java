package kz.balaguide.notification_module.services.messagesender;

import kz.balaguide.common_module.core.entities.Receipt;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

public interface MessageSenderService {
    /**
     * Sends an email containing receipt details to the specified parent email address.
     *
     * <p>The email includes the order number, course name, price, tax information, and total payable amount.
     *
     * @param parentEmail the email address of the parent
     * @param coursePrice the price of the course
     * @param courseName the name of the course
     * @param receipt the {@link Receipt} object containing receipt details
     */
    //TODO delete BigDecimal coursePrice, String courseName and set Course object,
    //TODO delete parentEmail and set Parent object
    CompletableFuture<Void> sendReceiptToParentEmail(String parentEmail, BigDecimal coursePrice, String courseName, Receipt receipt);
}
