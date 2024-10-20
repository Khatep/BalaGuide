package org.khatep.balaguide.dao;

import org.khatep.balaguide.models.entities.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {

    int save(Course course);

    int update(Course course);

    int deleteById(Long courseId);

    List<Course> findAll();

    Optional<Course> findById(Long courseId);

    List<Course> findByNameContainingIgnoreCase(String courseName);

    int addParticipant(Long childId, Long courseId);
}
