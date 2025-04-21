package kz.balaguide.teacher_module.mappers;

import kz.balaguide.common_module.core.entities.Teacher;
import kz.balaguide.teacher_module.dto.CreateTeacherRequest;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public Teacher mapCreateTeacherRequestToTeacher(CreateTeacherRequest createTeacherRequest) {
        return Teacher.builder()
                .firstName(createTeacherRequest.firstName())
                .lastName(createTeacherRequest.lastName())
                .phoneNumber(createTeacherRequest.phoneNumber())
                .birthDate(createTeacherRequest.birthDate())
                .email(createTeacherRequest.email())
                .salary(createTeacherRequest.salary())
                .gender(createTeacherRequest.gender())
                .build();
    }
}
