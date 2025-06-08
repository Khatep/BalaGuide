package kz.balaguide.teacher_module.controllers;

import jakarta.validation.Valid;
import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.entities.Teacher;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import kz.balaguide.teacher_module.dto.CreateTeacherRequest;
import kz.balaguide.teacher_module.services.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {
    private final TeacherService teacherService;
    private final ResponseMetadataService responseMetadataService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Teacher>> createTeacher(@Valid @RequestBody CreateTeacherRequest createTeacherRequest) {
        Teacher teacher = teacherService.createTeacher(createTeacherRequest);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2200);
        ApiResponse<Teacher> apiResponse = ApiResponse.<Teacher>builder()
                .responseMetadata(responseMetadata)
                .data(teacher)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{educationCenterId}")
    public ResponseEntity<ApiResponse<List<Teacher>>> getAllTeacherByEducationCenterId(@PathVariable Long educationCenterId) {
        List<Teacher> teachers = teacherService.findAllTeachersByEducationCenterId(educationCenterId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2200);
        ApiResponse<List<Teacher>> apiResponse = ApiResponse.<List<Teacher>>builder()
                .responseMetadata(responseMetadata)
                .data(teachers)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/generate-qr/{lessonId}")
    public ResponseEntity<ApiResponse<String>> generateQrCode(@PathVariable Long lessonId) {
        String qrImageBase64 = teacherService.generateQrCodeForLesson(lessonId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2200);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .responseMetadata(responseMetadata)
                .data(qrImageBase64)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/qr/{lessonId}")
    public ResponseEntity<ApiResponse<String>> getQrCode(@PathVariable Long lessonId) {
        String qrImageBase64 = teacherService.getExistingQrCode(lessonId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2200);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .responseMetadata(responseMetadata)
                .data(qrImageBase64)
                .build();

        return ResponseEntity.ok(apiResponse);
    }


}
