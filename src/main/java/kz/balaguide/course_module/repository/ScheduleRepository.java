package kz.balaguide.course_module.repository;

import kz.balaguide.common_module.core.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("""
    SELECT DISTINCT s
    FROM Schedule s
    JOIN Lesson l ON l.schedule = s
    WHERE l.group.course.educationCenter.id = :educationCenterId
    """)
    List<Schedule> findByEducationCenterId(@Param("educationCenterId") Long educationCenterId);

}
