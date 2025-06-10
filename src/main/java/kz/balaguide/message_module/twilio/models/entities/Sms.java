package kz.balaguide.message_module.twilio.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auth_user_id")
    private Long authUserId;

    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "message_sid")
    private String messageSid;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

}
