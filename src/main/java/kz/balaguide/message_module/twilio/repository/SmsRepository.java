package kz.balaguide.message_module.twilio.repository;

import kz.balaguide.message_module.twilio.models.entities.Sms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SmsRepository extends JpaRepository<Sms, Long> {

    @Query(value = """
        SELECT *
        FROM sms
        WHERE sms.auth_user_id = :authUserId
        ORDER BY sms.sent_at DESC
        LIMIT 1
        """, nativeQuery = true)
    Optional<Sms> findTopByAuthUserId(@Param("authUserId") Long authUserId);

}
