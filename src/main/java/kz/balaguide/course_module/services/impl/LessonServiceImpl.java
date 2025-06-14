package kz.balaguide.course_module.services.impl;

import kz.balaguide.common_module.core.entities.Lesson;
import kz.balaguide.course_module.repository.LessonRepository;
import kz.balaguide.course_module.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;


    @Override
    public List<Lesson> findByGroupId(Long groupId) {
        return lessonRepository.findAllByGroupId(groupId);
    }

    @Override
    public Lesson getLessonById(Long lessonId) {
        return lessonRepository.getLessonById(lessonId);
    }

}
