package kz.balaguide.course_module.services;

import kz.balaguide.common_module.core.entities.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> findByGroupId(Long groupId);

    Lesson getLessonById(Long lessonId);
}
