package kz.balaguide.message_module.email.kafka;

import kz.balaguide.message_module.email.messagesender.MessageSenderService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.course_module.dto.CourseDto;
import kz.balaguide.common_module.core.entities.Parent;
import kz.balaguide.common_module.core.entities.Receipt;
import kz.balaguide.course_module.repository.CourseRepository;
import kz.balaguide.parent_module.repository.ParentRepository;
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

        Long parentId = receipt.getPayment().getParent().getId();
        String parentEmail = parentRepository.findById(parentId)
                .map(Parent::getEmail)
                .orElseThrow(() -> new ParentNotFoundException("Parent not found with Id: " + parentId));

        CourseDto courseDto = courseRepository.findCoursePriceAndNameById(receipt.getPayment().getCourse().getId());

        messageSenderService.sendReceiptToParentEmail(parentEmail, courseDto.price(), courseDto.name(), receipt);
    }
}
