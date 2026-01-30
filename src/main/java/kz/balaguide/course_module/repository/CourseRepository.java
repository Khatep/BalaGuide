package kz.balaguide.course_module.repository;

import kz.balaguide.course_module.dto.CourseDto;
import kz.balaguide.common_module.core.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :courseName, '%'))")
    List<Course> findByNameContainingIgnoreCase(@Param("courseName") String courseName);

    @Query("SELECT new kz.balaguide.course_module.dto.CourseDto(c.name, c.price) FROM Course c WHERE c.id = :courseId")
    CourseDto findCoursePriceAndNameById(@Param("courseId") Long courseId);

    @Query("SELECT c FROM Course c JOIN FETCH c.groups")
    List<Course> findAllWithGroups();
}
