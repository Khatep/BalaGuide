package org.khatep.balaguide.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.khatep.balaguide.models.dto.CourseDto;
import org.khatep.balaguide.models.entities.Parent;
import org.khatep.balaguide.models.entities.Receipt;
import org.khatep.balaguide.repositories.CourseRepository;
import org.khatep.balaguide.repositories.ParentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private final JavaMailSender mailSender;
    private final ParentRepository parentRepository;
    private final CourseRepository courseRepository;
    private final Random orderNumberGenerator = new Random();

    /**
     * Kafka listener that processes incoming {@link Receipt} messages from the "receipt" topic.
     *
     * <p>Retrieves the parent's email based on the {@code parentId} in the {@link Receipt},
     * fetches the course price and name, and sends an email to the parent with the receipt details.
     *
     * @param receiptConsumerRecord the incoming Kafka message containing a {@link Receipt} object
     * @throws RuntimeException if the parent with the specified ID is not found
     */
    @KafkaListener(topics = "receipt", groupId = "email")
    public void listen(ConsumerRecord<String, Receipt> receiptConsumerRecord) {
        Receipt receipt = receiptConsumerRecord.value();

        String parentEmail = parentRepository.findById(receipt.getParentId())
                .map(Parent::getEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found with Id: " + receipt.getParentId()));

        CourseDto courseDto = courseRepository.findCoursePriceAndNameById(receipt.getCourseId());

        sendReceiptToParentEmail(parentEmail, courseDto.price(), courseDto.name(), receipt);
    }
    
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
    private void sendReceiptToParentEmail(String parentEmail, BigDecimal coursePrice, String courseName, Receipt receipt) {
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
                        "Date: " + receipt.getDateOfCreated() +
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
    }
}
