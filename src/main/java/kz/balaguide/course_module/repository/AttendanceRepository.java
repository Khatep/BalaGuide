package kz.balaguide.course_module.repository;

import kz.balaguide.common_module.core.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    boolean existsByChildIdAndLessonId(Long childId, Long lessonId);
}

