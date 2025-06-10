package kz.balaguide.course_module.controllers;


import jakarta.validation.Valid;
import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import kz.balaguide.common_module.core.entities.Lesson;
import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import kz.balaguide.course_module.dto.*;
import kz.balaguide.course_module.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ResponseMetadataService responseMetadataService;

    // Получить расписание на неделю
    @GetMapping("/week")
    public ResponseEntity<ApiResponse<List<ScheduleItemDto>>> getWeeklySchedule(
            @RequestParam Long educationCenterId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        List<ScheduleItemDto> schedule = scheduleService.getWeeklySchedule(educationCenterId, startDate);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2000);
        ApiResponse<List<ScheduleItemDto>> apiResponse = ApiResponse.<List<ScheduleItemDto>>builder()
                .responseMetadata(responseMetadata)
                .data(schedule)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // Получить расписание по фильтрам
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<ScheduleItemDto>>> getFilteredSchedule(
            @RequestParam Long educationCenterId,
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) Long courseId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        ScheduleFilterDto filter = ScheduleFilterDto.builder()
                .educationCenterId(educationCenterId)
                .teacherId(teacherId)
                .groupId(groupId)
                .courseId(courseId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        List<ScheduleItemDto> schedule = scheduleService.getFilteredSchedule(filter);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2000);
        ApiResponse<List<ScheduleItemDto>> apiResponse = ApiResponse.<List<ScheduleItemDto>>builder()
                .responseMetadata(responseMetadata)
                .data(schedule)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // Создать новое занятие в расписании
    @PostMapping("/lesson")
    public ResponseEntity<ApiResponse<ScheduleItemDto>> createScheduledLesson(
            @RequestBody @Valid CreateScheduledLessonRequest request) {

        ScheduleItemDto scheduledLesson = scheduleService.createScheduledLesson(request);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2000);
        ApiResponse<ScheduleItemDto> apiResponse = ApiResponse.<ScheduleItemDto>builder()
                .responseMetadata(responseMetadata)
                .data(scheduledLesson)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // Обновить время занятия (перенести)
    @PutMapping("/lesson/{lessonId}/reschedule")
    public ResponseEntity<ApiResponse<ScheduleItemDto>> rescheduleLesson(
            @PathVariable Long lessonId,
            @RequestBody @Valid RescheduleLessonRequest request) {

        ScheduleItemDto rescheduledLesson = scheduleService.rescheduleLesson(lessonId, request);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2000);
        ApiResponse<ScheduleItemDto> apiResponse = ApiResponse.<ScheduleItemDto>builder()
                .responseMetadata(responseMetadata)
                .data(rescheduledLesson)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // Получить статистику расписания
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<ScheduleStatsDto>> getScheduleStats(
            @RequestParam Long educationCenterId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        ScheduleStatsDto stats = scheduleService.getScheduleStats(educationCenterId, startDate, endDate);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2000);
        ApiResponse<ScheduleStatsDto> apiResponse = ApiResponse.<ScheduleStatsDto>builder()
                .responseMetadata(responseMetadata)
                .data(stats)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

//    // Проверить конфликты в расписании
//    @PostMapping("/check-conflicts")
//    public ResponseEntity<ApiResponse<List<ScheduleConflictDto>>> checkScheduleConflicts(
//            @RequestBody @Valid CheckConflictsRequest request) {
//
//        List<ScheduleConflictDto> conflicts = scheduleService.checkScheduleConflicts(request);
//
//        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2000);
//        ApiResponse<List<ScheduleConflictDto>> apiResponse = ApiResponse.<List<ScheduleConflictDto>>builder()
//                .responseMetadata(responseMetadata)
//                .data(conflicts)
//                .build();
//
//        return ResponseEntity.ok(apiResponse);
//    }

    // Получить доступные временные слоты
    @GetMapping("/available-slots")
    public ResponseEntity<ApiResponse<List<TimeSlotDto>>> getAvailableTimeSlots(
            @RequestParam Long educationCenterId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) Long roomId) {

        List<TimeSlotDto> availableSlots = scheduleService.getAvailableTimeSlots(educationCenterId, date);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2000);
        ApiResponse<List<TimeSlotDto>> apiResponse = ApiResponse.<List<TimeSlotDto>>builder()
                .responseMetadata(responseMetadata)
                .data(availableSlots)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // Массовое создание расписания (например, на семестр)
    @PostMapping("/bulk-create")
    public ResponseEntity<ApiResponse<List<ScheduleItemDto>>> bulkCreateSchedule(
            @RequestBody @Valid BulkCreateScheduleRequest request) {

        List<ScheduleItemDto> createdLessons = scheduleService.bulkCreateSchedule(request);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2000);
        ApiResponse<List<ScheduleItemDto>> apiResponse = ApiResponse.<List<ScheduleItemDto>>builder()
                .responseMetadata(responseMetadata)
                .data(createdLessons)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
