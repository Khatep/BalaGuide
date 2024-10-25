package org.khatep.balaguide.repositories;

import org.khatep.balaguide.models.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :courseName, '%'))")
    List<Course> findByNameContainingIgnoreCase(@Param("courseName") String courseName);

    @Query("SELECT c FROM Course c JOIN c.children ch WHERE ch.id = :childId")
    List<Course> findAllByChildId(@Param("childId") Long childId);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
            "FROM child_course " +
            "WHERE child_id = :childId AND course_id = :courseId", nativeQuery = true)
    boolean isChildEnrolledInCourse(@Param("courseId") Long courseId, @Param("childId") Long childId);

    @Modifying
    @Query(value = "INSERT INTO child_course (child_id, course_id) VALUES (:childId, :courseId)", nativeQuery = true)
    void enrollChildInCourse(@Param("childId") Long childId, @Param("courseId") Long courseId);
}
