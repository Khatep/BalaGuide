package kz.balaguide.child_module.mappers;

import kz.balaguide.common_module.core.dtos.responses.ChildDto;
import kz.balaguide.common_module.core.dtos.responses.CourseInfoDto;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.parent_module.dtos.CreateChildRequest;
import org.springframework.stereotype.Component;

@Component
public class ChildMapper {
    public Child mapCreateChildRequestToChild(final CreateChildRequest createChildRequest) {
        return Child.builder()
                .firstName(createChildRequest.firstName())
                .lastName(createChildRequest.lastName())
                .phoneNumber(createChildRequest.phoneNumber())
                .birthDate(createChildRequest.birthDate())
                .email(createChildRequest.email())
                .gender(createChildRequest.gender())
                .build();
    }

    public ChildDto toChildDto(Child child) {
        return ChildDto.builder()
                .id(child.getId())
                .firstName(child.getFirstName())
                .lastName(child.getLastName())
                .phoneNumber(child.getPhoneNumber())
                .email(child.getEmail())
                .birthDate(child.getBirthDate())
                .gender(child.getGender())
                .parentId(child.getParent() != null ? child.getParent().getId() : null)
                .parentFullName(child.getParent() != null
                        ? child.getParent().getFirstName() + " " + child.getParent().getLastName()
                        : null)
                .enrolledCourses(child.getGroupsEnrolled().stream()
                        .map(group -> CourseInfoDto.builder()
                                .courseId(group.getCourse().getId())
                                .courseName(group.getCourse().getName())
                                .groupName(group.getName())
                                .language(group.getLanguage())
                                //.teacherFullName(group.getTeacher().getFirstName() + " " + group.getTeacher().getLastName())
                                .build())
                        .toList())
                .build();
    }
}
