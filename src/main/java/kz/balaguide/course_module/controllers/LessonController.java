package kz.balaguide.course_module.controllers;

import feign.FeignException;
import kz.balaguide.common_module.core.entities.Lesson;
import kz.balaguide.course_module.services.LessonService;
import kz.balaguide.sftp_module.clients.SftpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@Controller
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    private final SftpClient sftpClient;

    @GetMapping("/{lessonId}/download")
    public ResponseEntity<byte[]> downloadLessonFile(@PathVariable Long lessonId) throws FileNotFoundException {
        Lesson lesson = lessonService.getLessonById(lessonId);

        if (lesson.getFileUrl() == null || lesson.getFileUrl().isEmpty()) {
            throw new IllegalStateException("Lesson has no file attached");
        }

        try {
            ResponseEntity<byte[]> sftpResponse = sftpClient.download(lesson.getFileUrl());
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + lesson.getFileUrl())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(sftpResponse.getBody());
        } catch (FeignException.NotFound e) {
            throw new FileNotFoundException("Файл не найден на SFTP-сервере");
        }
    }

    @PostMapping("/{lessonId}/upload")
    public ResponseEntity<String> uploadLessonFile(
            @PathVariable Long lessonId,
            @RequestBody byte[] fileBytes,
            @RequestParam String fileName
    ) {
        Lesson lesson = lessonService.getLessonById(lessonId);

        if (lesson == null) {
            throw new IllegalStateException("Lesson with id " + lessonId + " not found");
        }

        try {
            sftpClient.upload(fileBytes, fileName);
            return ResponseEntity.status(HttpStatus.OK).body("✅ File uploaded and lesson updated.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Upload failed: " + e.getMessage());
        }
    }
}
