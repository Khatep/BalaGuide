package kz.balaguide.message_module.twilio.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
@Slf4j
public class SmsService {

    @Value("${twilio.phone-number}")
    private String from;

    public String sendSms(String to, String body) {
        String toFormatted = "+" + to;
        try {
            Message msg = Message.creator(
                    new PhoneNumber(toFormatted),
                    new PhoneNumber(from),
                    body
            ).create();
            log.info("SMS sent to {}, SID: {}", to, msg.getSid());
            return msg.getSid();
        } catch (Exception e) {
            log.error("Failed to send SMS to {}", to, e);
            throw new RuntimeException("Twilio SMS failed", e);
        }
    }
}
