package kz.balaguide.teacher_module.services;

import kz.balaguide.common_module.core.entities.Teacher;
import kz.balaguide.teacher_module.dto.CreateTeacherRequest;

public interface TeacherService {
    Teacher createTeacher(CreateTeacherRequest createTeacherRequest);

    Teacher findTeacherById(Long id);
}
