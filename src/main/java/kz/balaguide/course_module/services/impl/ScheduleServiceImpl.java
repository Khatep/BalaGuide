package kz.balaguide.course_module.services.impl;

import kz.balaguide.common_module.core.entities.Group;
import kz.balaguide.common_module.core.entities.Lesson;
import kz.balaguide.common_module.core.entities.Schedule;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.GroupNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.LessonNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.schedule.ScheduleConflictException;
import kz.balaguide.course_module.dto.*;
import kz.balaguide.course_module.repository.*;
import kz.balaguide.course_module.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public List<ScheduleItemDto> getWeeklySchedule(Long educationCenterId, LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6);

        // Получаем все уроки в диапазоне дат для образовательного центра
        List<Lesson> lessons = lessonRepository.findByGroupEducationCenterIdAndDateBetween(
                educationCenterId, startDate, endDate);

        return lessons.stream()
                .map(this::mapLessonToScheduleItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleItemDto> getFilteredSchedule(ScheduleFilterDto filter) {
        List<Lesson> lessons;

        // Применяем фильтры
        if (filter.getTeacherId() != null && filter.getGroupId() != null) {
            lessons = lessonRepository.findByGroupTeacherIdAndGroupIdAndDateBetween(
                    filter.getTeacherId(), filter.getGroupId(), filter.getStartDate(), filter.getEndDate());
        } else if (filter.getTeacherId() != null) {
            lessons = lessonRepository.findByGroupTeacherIdAndDateBetween(
                    filter.getTeacherId(), filter.getStartDate(), filter.getEndDate());
        } else if (filter.getGroupId() != null) {
            lessons = lessonRepository.findByGroupIdAndDateBetween(
                    filter.getGroupId(), filter.getStartDate(), filter.getEndDate());
        } else if (filter.getCourseId() != null) {
            lessons = lessonRepository.findByGroupCourseIdAndDateBetween(
                    filter.getCourseId(), filter.getStartDate(), filter.getEndDate());
        } else {
            lessons = lessonRepository.findByGroupEducationCenterIdAndDateBetween(
                    filter.getEducationCenterId(), filter.getStartDate(), filter.getEndDate());
        }

        return lessons.stream()
                .map(this::mapLessonToScheduleItemDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ScheduleItemDto createScheduledLesson(CreateScheduledLessonRequest request) {
        // Проверяем существование группы
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new GroupNotFoundException("Group not found with id: " + request.getGroupId()));

        // Проверяем существование расписания
        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + request.getScheduleId()));

        // Проверяем, что день недели соответствует расписанию
        DayOfWeek requestDayOfWeek = request.getDate().getDayOfWeek();
        if (!requestDayOfWeek.equals(schedule.getDayOfWeek())) {
            throw new IllegalArgumentException("Date day of week (" + requestDayOfWeek +
                    ") does not match schedule day of week (" + schedule.getDayOfWeek() + ")");
        }

        // Проверяем конфликты
        CheckConflictsRequest conflictsRequest = CheckConflictsRequest.builder()
                .date(request.getDate())
                .scheduleId(request.getScheduleId())
                .groupId(request.getGroupId())
                .educationCenterId(group.getCourse().getEducationCenter().getId())
                .build();

        List<ScheduleConflictDto> conflicts = checkScheduleConflicts(conflictsRequest);
        if (!conflicts.isEmpty()) {
            throw new ScheduleConflictException("Cannot create lesson due to conflicts: " +
                    conflicts.stream().map(ScheduleConflictDto::getMessage).collect(Collectors.joining(", ")));
        }

        // Проверяем уникальность номера урока для группы
        if (lessonRepository.existsByGroupIdAndLessonNumber(request.getGroupId(), request.getLessonNumber())) {
            throw new IllegalArgumentException("Lesson with number " + request.getLessonNumber() +
                    " already exists for this group");
        }

        // Создаем новый урок
        Lesson lesson = Lesson.builder()
                .lessonNumber(request.getLessonNumber())
                .topic(request.getTopic())
                .description(request.getDescription())
                .fileUrl(request.getFileUrl())
                .group(group)
                .date(request.getDate())
                .schedule(schedule)
                .build();

        Lesson savedLesson = lessonRepository.save(lesson);
        return mapLessonToScheduleItemDto(savedLesson);
    }

    @Override
    @Transactional
    public ScheduleItemDto rescheduleLesson(Long lessonId, RescheduleLessonRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found with id: " + lessonId));

        // Если меняется расписание, проверяем его существование
        Schedule newSchedule = lesson.getSchedule();
        if (request.getNewScheduleId() != null) {
            newSchedule = scheduleRepository.findById(request.getNewScheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + request.getNewScheduleId()));

            // Проверяем, что день недели соответствует новому расписанию
            DayOfWeek requestDayOfWeek = request.getNewDate().getDayOfWeek();
            if (!requestDayOfWeek.equals(newSchedule.getDayOfWeek())) {
                throw new IllegalArgumentException("New date day of week (" + requestDayOfWeek +
                        ") does not match new schedule day of week (" + newSchedule.getDayOfWeek() + ")");
            }
        }

        // Проверяем конфликты
        CheckConflictsRequest conflictsRequest = CheckConflictsRequest.builder()
                .date(request.getNewDate())
                .scheduleId(newSchedule.getId())
                .groupId(lesson.getGroup().getId())
                .educationCenterId(lesson.getGroup().getCourse().getEducationCenter().getId())
                .excludeLessonId(lessonId)
                .build();

        List<ScheduleConflictDto> conflicts = checkScheduleConflicts(conflictsRequest);
        if (!conflicts.isEmpty()) {
            throw new ScheduleConflictException("Cannot reschedule lesson due to conflicts: " +
                    conflicts.stream().map(ScheduleConflictDto::getMessage).collect(Collectors.joining(", ")));
        }

        // Обновляем урок
        lesson.setDate(request.getNewDate());
        if (request.getNewScheduleId() != null) {
            lesson.setSchedule(newSchedule);
        }

        Lesson updatedLesson = lessonRepository.save(lesson);
        return mapLessonToScheduleItemDto(updatedLesson);
    }

    @Override
    public ScheduleStatsDto getScheduleStats(Long educationCenterId, LocalDate startDate, LocalDate endDate) {
        List<Lesson> lessons = lessonRepository.findByGroupEducationCenterIdAndDateBetween(
                educationCenterId, startDate, endDate);

        int totalLessons = lessons.size();

        Set<Long> uniqueGroups = new HashSet<>();
        Set<Long> uniqueTeachers = new HashSet<>();
        int totalStudents = 0;

        for (Lesson lesson : lessons) {
            uniqueGroups.add(lesson.getGroup().getId());
            uniqueTeachers.add(lesson.getGroup().getTeacher().getId());
            totalStudents += lesson.getGroup().getCurrentParticipants();
        }

        double averageClassSize = totalLessons > 0 ? (double) totalStudents / totalLessons : 0;

        // Для упрощения считаем все уроки как предстоящие, так как нет поля status
        return ScheduleStatsDto.builder()
                .totalLessons(totalLessons)
                .totalStudents(totalStudents)
                .totalTeachers(uniqueTeachers.size())
                .totalGroups(uniqueGroups.size())
                .averageClassSize(averageClassSize)
                .completedLessons(0) // Нет информации о статусе
                .cancelledLessons(0) // Нет информации о статусе
                .upcomingLessons(totalLessons) // Считаем все как предстоящие
                .build();
    }

    @Override
    public List<ScheduleConflictDto> checkScheduleConflicts(CheckConflictsRequest request) {
        List<ScheduleConflictDto> conflicts = new ArrayList<>();

        // Получаем расписание для проверки времени
        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + request.getScheduleId()));

        // Проверяем конфликты для группы в указанную дату и время
        if (request.getGroupId() != null) {
            List<Lesson> groupLessons = lessonRepository.findByGroupIdAndDateAndScheduleTimeOverlap(
                    request.getGroupId(), request.getDate(), schedule.getStartTime(), schedule.getEndTime());

            if (request.getExcludeLessonId() != null) {
                groupLessons = groupLessons.stream()
                        .filter(lesson -> !lesson.getId().equals(request.getExcludeLessonId()))
                        .collect(Collectors.toList());
            }

            if (!groupLessons.isEmpty()) {
                for (Lesson lesson : groupLessons) {
                    conflicts.add(ScheduleConflictDto.builder()
                            .conflictType("GROUP")
                            .message("Group is already scheduled for another lesson at this time")
                            .conflictingLesson(mapLessonToScheduleItemDto(lesson))
                            .build());
                }
            }
        }

        // Проверяем конфликты для учителя группы
        if (request.getGroupId() != null) {
            Group group = groupRepository.findById(request.getGroupId()).orElse(null);
            if (group != null && group.getTeacher() != null) {
                List<Lesson> teacherLessons = lessonRepository.findByGroupTeacherIdAndDateAndScheduleTimeOverlap(
                        group.getTeacher().getId(), request.getDate(), schedule.getStartTime(), schedule.getEndTime());

                if (request.getExcludeLessonId() != null) {
                    teacherLessons = teacherLessons.stream()
                            .filter(lesson -> !lesson.getId().equals(request.getExcludeLessonId()))
                            .collect(Collectors.toList());
                }

                if (!teacherLessons.isEmpty()) {
                    for (Lesson lesson : teacherLessons) {
                        conflicts.add(ScheduleConflictDto.builder()
                                .conflictType("TEACHER")
                                .message("Teacher is already scheduled for another lesson at this time")
                                .conflictingLesson(mapLessonToScheduleItemDto(lesson))
                                .build());
                    }
                }
            }
        }

        return conflicts;
    }

    @Override
    public List<TimeSlotDto> getAvailableTimeSlots(Long educationCenterId, LocalDate date) {
        // Получаем все расписания для образовательного центра
        List<Schedule> allSchedules = scheduleRepository.findByEducationCenterId(educationCenterId);

        // Фильтруем по дню недели
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<Schedule> daySchedules = allSchedules.stream()
                .filter(schedule -> schedule.getDayOfWeek().equals(dayOfWeek))
                .collect(Collectors.toList());

        // Получаем занятые слоты на эту дату
        List<Lesson> occupiedLessons = lessonRepository.findByGroupEducationCenterIdAndDate(
                educationCenterId, date);

        Set<Long> occupiedScheduleIds = occupiedLessons.stream()
                .map(lesson -> lesson.getSchedule().getId())
                .collect(Collectors.toSet());

        // Создаем список доступных слотов
        return daySchedules.stream()
                .map(schedule -> {
                    boolean isAvailable = !occupiedScheduleIds.contains(schedule.getId());
                    int duration = (int) ChronoUnit.MINUTES.between(schedule.getStartTime(), schedule.getEndTime());

                    return TimeSlotDto.builder()
                            .scheduleId(schedule.getId())
                            .startTime(schedule.getStartTime())
                            .endTime(schedule.getEndTime())
                            .dayOfWeek(schedule.getDayOfWeek())
                            .durationMinutes(duration)
                            .isAvailable(isAvailable)
                            .conflictReason(isAvailable ? null : "Time slot is already occupied")
                            .timeZone(schedule.getTimeZone())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TimeSlotDto> getAllScheduleSlots(Long educationCenterId) {
        List<Schedule> schedules = scheduleRepository.findByEducationCenterId(educationCenterId);

        return schedules.stream()
                .map(schedule -> {
                    int duration = (int) ChronoUnit.MINUTES.between(schedule.getStartTime(), schedule.getEndTime());

                    return TimeSlotDto.builder()
                            .scheduleId(schedule.getId())
                            .startTime(schedule.getStartTime())
                            .endTime(schedule.getEndTime())
                            .dayOfWeek(schedule.getDayOfWeek())
                            .durationMinutes(duration)
                            .isAvailable(true)
                            .timeZone(schedule.getTimeZone())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ScheduleItemDto> bulkCreateSchedule(BulkCreateScheduleRequest request) {
        List<Lesson> createdLessons = new ArrayList<>();

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new GroupNotFoundException("Group not found with id: " + request.getGroupId()));

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + request.getScheduleId()));

        int repeatEveryNWeeks = request.getRepeatEveryNWeeks() != null ? request.getRepeatEveryNWeeks() : 1;
        Set<LocalDate> excludeDates = request.getExcludeDates() != null ?
                new HashSet<>(request.getExcludeDates()) : new HashSet<>();

        // Находим все даты, которые соответствуют дню недели из расписания
        List<LocalDate> targetDates = new ArrayList<>();
        LocalDate currentDate = request.getStartDate();
        int weekCounter = 0;

        while (!currentDate.isAfter(request.getEndDate())) {
            if (weekCounter % repeatEveryNWeeks == 0) {
                LocalDate targetDate = currentDate.with(TemporalAdjusters.nextOrSame(schedule.getDayOfWeek()));

                if (!targetDate.isAfter(request.getEndDate()) && !excludeDates.contains(targetDate)) {
                    targetDates.add(targetDate);
                }
            }

            currentDate = currentDate.plusWeeks(1);
            weekCounter++;
        }

        // Создаем уроки для каждой даты и каждого шаблона урока
        int lessonCounter = 0;
        for (LocalDate targetDate : targetDates) {
            for (LessonTemplateDto lessonTemplate : request.getLessons()) {
                lessonCounter++;

                // Проверяем конфликты
                CheckConflictsRequest conflictsRequest = CheckConflictsRequest.builder()
                        .date(targetDate)
                        .scheduleId(request.getScheduleId())
                        .groupId(request.getGroupId())
                        .educationCenterId(group.getCourse().getEducationCenter().getId())
                        .build();

                List<ScheduleConflictDto> conflicts = checkScheduleConflicts(conflictsRequest);

                if (conflicts.isEmpty()) {
                    // Используем номер урока из шаблона или генерируем автоматически
                    Integer lessonNumber = lessonTemplate.getLessonNumber() != null ?
                            lessonTemplate.getLessonNumber() : lessonCounter;

                    // Проверяем уникальность номера урока
                    if (!lessonRepository.existsByGroupIdAndLessonNumber(request.getGroupId(), lessonNumber)) {
                        Lesson lesson = Lesson.builder()
                                .lessonNumber(lessonNumber)
                                .topic(lessonTemplate.getTopic())
                                .description(lessonTemplate.getDescription())
                                .fileUrl(lessonTemplate.getFileUrl())
                                .group(group)
                                .date(targetDate)
                                .schedule(schedule)
                                .build();

                        Lesson savedLesson = lessonRepository.save(lesson);
                        createdLessons.add(savedLesson);
                    } else {
                        log.warn("Lesson number {} already exists for group {}", lessonNumber, request.getGroupId());
                    }
                } else {
                    log.warn("Conflict detected for lesson on {}: {}",
                            targetDate,
                            conflicts.stream().map(ScheduleConflictDto::getMessage).collect(Collectors.joining(", ")));
                }
            }
        }

        return createdLessons.stream()
                .map(this::mapLessonToScheduleItemDto)
                .collect(Collectors.toList());
    }

    // Вспомогательные методы

    private ScheduleItemDto mapLessonToScheduleItemDto(Lesson lesson) {
        Schedule schedule = lesson.getSchedule();
        int duration = (int) ChronoUnit.MINUTES.between(schedule.getStartTime(), schedule.getEndTime());

        return ScheduleItemDto.builder()
                .lessonId(lesson.getId())
                .lessonNumber(lesson.getLessonNumber())
                .topic(lesson.getTopic())
                .description(lesson.getDescription())
                .courseName(lesson.getGroup().getCourse().getName())
                .groupName(lesson.getGroup().getName())
                .teacherName(lesson.getGroup().getTeacher().getFirstName())
                .date(lesson.getDate())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .dayOfWeek(schedule.getDayOfWeek())
                .durationMinutes(duration)
                .color(getCourseColor(lesson.getGroup().getCourse().getId()))
                .hasFile(lesson.getFileUrl() != null && !lesson.getFileUrl().isEmpty())
                .scheduleId(schedule.getId())
                .timeZone(schedule.getTimeZone())
                .build();
    }

    private String getCourseColor(Long courseId) {
        String[] colors = {
                "bg-blue-500", "bg-green-500", "bg-purple-500",
                "bg-orange-500", "bg-pink-500", "bg-indigo-500",
                "bg-teal-500", "bg-red-500", "bg-yellow-500"
        };

        int index = (int) (courseId % colors.length);
        return colors[index];
    }
}
