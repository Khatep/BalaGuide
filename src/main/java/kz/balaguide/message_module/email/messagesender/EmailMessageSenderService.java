package kz.balaguide.message_module.email.messagesender;

import lombok.RequiredArgsConstructor;
import kz.balaguide.common_module.core.entities.Receipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

//TODO make async
@Service
@RequiredArgsConstructor
public class EmailMessageSenderService implements MessageSenderService {

    private static final Logger log = LoggerFactory.getLogger(EmailMessageSenderService.class);
    private final JavaMailSender mailSender;
    private final Random orderNumberGenerator = new Random();

    @Async
    public CompletableFuture<Void> sendReceiptToParentEmail(String parentEmail, BigDecimal coursePrice, String courseName, Receipt receipt) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(parentEmail);

        message.setSubject("Receipt");
        message.setText(
                "Bala Guide Ltd.\n" +
                        "TIN: 1234567890, \n" +
                        "KPP: 0987654321\n" +
                        "Almaty city, Saina street, h. 79\n" +
                        "Phone number. +7(771) 285 86 06\n" +
                        "\n" +
                        String.format("%40s", "RECEIPT") +
                        "\n" +
                        "\n" +
                        "Order number: " + orderNumberGenerator.nextInt(1001) +
                        '\n' +
                        "Date: " + receipt.getCreatedDate() +
                        "\n" +
                        "\n" +
                        "Name: " + courseName +
                        "\n\n" +
                        "TOTAL: " + coursePrice + " KZT\n" +
                        "VAT (12%): " + (coursePrice.multiply(new BigDecimal("0.12"))) + " KZT\n" +
                        "\n" +
                        "PAYABLE: " + coursePrice + " KZT\n" +
                        "Payment method: Bank card\n" +
                        "\n" +
                        "Thanks for your purchase,\n" +
                        "\n" +
                        "Your Bala Guide team"
        );

        mailSender.send(message);
        //Todo check
        return CompletableFuture.runAsync(() -> log.info("Email sent to " + parentEmail));
    }
}
