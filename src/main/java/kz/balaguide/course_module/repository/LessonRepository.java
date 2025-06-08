package kz.balaguide.course_module.repository;


import kz.balaguide.common_module.core.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByGroupId(Long groupId);

    Lesson getLessonById(Long lessonId);

    // Методы для работы с новой структурой
    List<Lesson> findByGroupEducationCenterIdAndDateBetween(Long educationCenterId, LocalDate startDate, LocalDate endDate);

    List<Lesson> findByGroupEducationCenterIdAndDate(Long educationCenterId, LocalDate date);

    List<Lesson> findByGroupIdAndDateBetween(Long groupId, LocalDate startDate, LocalDate endDate);

    List<Lesson> findByGroupTeacherIdAndDateBetween(Long teacherId, LocalDate startDate, LocalDate endDate);

    List<Lesson> findByGroupCourseIdAndDateBetween(Long courseId, LocalDate startDate, LocalDate endDate);

    List<Lesson> findByGroupTeacherIdAndGroupIdAndDateBetween(Long teacherId, Long groupId, LocalDate startDate, LocalDate endDate);

    boolean existsByGroupIdAndLessonNumber(Long groupId, Integer lessonNumber);

    @Query("SELECT l FROM Lesson l WHERE l.group.id = :groupId AND l.date = :date AND " +
            "((l.schedule.startTime < :endTime AND l.schedule.endTime > :startTime))")
    List<Lesson> findByGroupIdAndDateAndScheduleTimeOverlap(@Param("groupId") Long groupId,
                                                            @Param("date") LocalDate date,
                                                            @Param("startTime") LocalTime startTime,
                                                            @Param("endTime") LocalTime endTime);

    @Query("SELECT l FROM Lesson l WHERE l.group.teacher.id = :teacherId AND l.date = :date AND " +
            "((l.schedule.startTime < :endTime AND l.schedule.endTime > :startTime))")
    List<Lesson> findByGroupTeacherIdAndDateAndScheduleTimeOverlap(@Param("teacherId") Long teacherId,
                                                                   @Param("date") LocalDate date,
                                                                   @Param("startTime") LocalTime startTime,
                                                                   @Param("endTime") LocalTime endTime);
}

