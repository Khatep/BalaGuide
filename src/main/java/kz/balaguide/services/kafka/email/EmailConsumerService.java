package kz.balaguide.services.kafka.email;

import kz.balaguide.services.messagesender.MessageSenderService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import kz.balaguide.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.core.dtos.CourseDto;
import kz.balaguide.core.entities.Parent;
import kz.balaguide.core.entities.Receipt;
import kz.balaguide.core.repositories.course.CourseRepository;
import kz.balaguide.core.repositories.parent.ParentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConsumerService {

    private final MessageSenderService messageSenderService;
    private final ParentRepository parentRepository;
    private final CourseRepository courseRepository;

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
    public void listenReceiptTopic(ConsumerRecord<String, Receipt> receiptConsumerRecord) {
        Receipt receipt = receiptConsumerRecord.value();

        String parentEmail = parentRepository.findById(receipt.getParentId())
                .map(Parent::getEmail)
                .orElseThrow(() -> new ParentNotFoundException("Parent not found with Id: " + receipt.getParentId()));

        CourseDto courseDto = courseRepository.findCoursePriceAndNameById(receipt.getCourseId());

        messageSenderService.sendReceiptToParentEmail(parentEmail, courseDto.price(), courseDto.name(), receipt);
    }
}
