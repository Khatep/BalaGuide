package kz.balaguide.message_module.twilio.models.dtos;

public record SmsOtpVerifyRequest(String phoneNumber, String otp) {

}
