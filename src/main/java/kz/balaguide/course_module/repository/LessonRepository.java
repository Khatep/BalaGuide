package kz.balaguide.course_module.repository;


import kz.balaguide.common_module.core.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
