package kz.balaguide.message_module.twilio.services;

import kz.balaguide.auth_module.repository.AuthUserRepository;
import kz.balaguide.common_module.core.entities.AuthUser;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.message_module.twilio.models.dtos.SmsOtpRequest;
import kz.balaguide.message_module.twilio.models.dtos.SmsOtpVerifyRequest;
import kz.balaguide.message_module.twilio.models.entities.Sms;
import kz.balaguide.message_module.twilio.repository.SmsRepository;
import kz.balaguide.message_module.twilio.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final SmsService smsService;
    private final SmsRepository smsRepository;
    private final AuthUserRepository authUserRepository;

    public void sendOtp(SmsOtpRequest request) {
        String phone = request.phoneNumber();
        AuthUser user = authUserRepository.findByPhoneNumber(phone)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        String otp = OtpUtil.generateOtp(6);
        String message = "Ваш код подтверждения: " + otp;

        String sid = smsService.sendSms(phone, message);

        Sms sms = Sms.builder()
                .authUserId(user.getId())
                .otpCode(otp)
                .messageSid(sid)
                .sentAt(LocalDateTime.now())
                .build();

        smsRepository.save(sms);
    }

    public boolean verifyOtp(SmsOtpVerifyRequest request) {

        Optional<AuthUser> authUser = authUserRepository.findByPhoneNumber(request.phoneNumber());
        Long authUserId;

        if (authUser.isPresent()) {
             authUserId = authUser.get().getId();
        } else {
            throw new ParentNotFoundException("Parent with phone" +  request.phoneNumber() + " not found");
        }

        return smsRepository.findTopByAuthUserId(authUserId)
                .map(sms -> request.otp().equals(sms.getOtpCode()))
                .orElse(false);
    }
}
