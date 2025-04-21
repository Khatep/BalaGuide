package kz.balaguide.course_module.mappers;

import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.Group;
import kz.balaguide.common_module.core.entities.Teacher;
import kz.balaguide.course_module.dto.CreateGroupRequest;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {
    public Group mapCreateGroupRequestToCourse(CreateGroupRequest createGroupRequest, Course course, Teacher teacher) {
        return Group.builder()
                .name(createGroupRequest.name())
                .course(course)
                .teacher(teacher)
                .maxParticipants(createGroupRequest.maxParticipants())
                .minParticipants(createGroupRequest.minParticipants())
                .language(createGroupRequest.language())
                .build();
    }
}
