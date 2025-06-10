package kz.balaguide.course_module.services;



import kz.balaguide.course_module.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    /**
     * Получить расписание на неделю для образовательного центра
     */
    List<ScheduleItemDto> getWeeklySchedule(Long educationCenterId, LocalDate startDate);

    /**
     * Получить расписание по фильтрам
     */
    List<ScheduleItemDto> getFilteredSchedule(ScheduleFilterDto filter);

    /**
     * Создать новое занятие в расписании
     */
    ScheduleItemDto createScheduledLesson(CreateScheduledLessonRequest request);

    /**
     * Перенести занятие на другую дату/время
     */
    ScheduleItemDto rescheduleLesson(Long lessonId, RescheduleLessonRequest request);

    /**
     * Получить статистику расписания
     */
    ScheduleStatsDto getScheduleStats(Long educationCenterId, LocalDate startDate, LocalDate endDate);

    /**
     * Проверить конфликты в расписании
     */
    List<ScheduleConflictDto> checkScheduleConflicts(CheckConflictsRequest request);

    /**
     * Получить доступные временные слоты для даты
     */
    List<TimeSlotDto> getAvailableTimeSlots(Long educationCenterId, LocalDate date);

    /**
     * Массовое создание расписания
     */
    List<ScheduleItemDto> bulkCreateSchedule(BulkCreateScheduleRequest request);

    /**
     * Получить все Schedule для образовательного центра
     */
    List<TimeSlotDto> getAllScheduleSlots(Long educationCenterId);
}
