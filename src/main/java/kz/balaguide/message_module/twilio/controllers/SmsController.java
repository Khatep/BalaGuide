package kz.balaguide.message_module.twilio.controllers;

import kz.balaguide.message_module.twilio.services.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import kz.balaguide.message_module.twilio.models.dtos.SmsOtpRequest;
import kz.balaguide.message_module.twilio.models.dtos.SmsOtpVerifyRequest;
import kz.balaguide.message_module.twilio.services.OtpService;

@RestController
@RequestMapping("/api/v1/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;
    private final OtpService otpService;

    @PostMapping
    public ResponseEntity<String> send(@RequestParam String to, @RequestParam String body) {
        String sid = smsService.sendSms(to, body);
        return ResponseEntity.ok("SMS SID: " + sid);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody SmsOtpRequest request) {
        otpService.sendOtp(request);
        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody SmsOtpVerifyRequest request) {
        boolean verified = otpService.verifyOtp(request);
        return verified
                ? ResponseEntity.ok("OTP verified successfully")
                : ResponseEntity.badRequest().body("Invalid or incorrect OTP");
    }
}
