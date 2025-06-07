package kz.balaguide.teacher_module.services;

import kz.balaguide.common_module.core.entities.Teacher;
import kz.balaguide.teacher_module.dto.CreateTeacherRequest;

import java.util.List;

public interface TeacherService {
    Teacher createTeacher(CreateTeacherRequest createTeacherRequest);

    Teacher findTeacherById(Long id);

    List<Teacher> findAllTeachersByEducationCenterId(Long educationCenterId);
}
