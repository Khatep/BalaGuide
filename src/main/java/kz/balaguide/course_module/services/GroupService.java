package kz.balaguide.course_module.services;

import jakarta.validation.Valid;
import kz.balaguide.common_module.core.entities.Group;
import kz.balaguide.course_module.dto.CreateGroupRequest;
import kz.balaguide.course_module.dto.EnrollmentActionDto;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    boolean unenrollChild(EnrollmentActionDto enrollmentActionDto);

    Group createGroup(@Valid CreateGroupRequest createGroupRequest);

    Optional<Group> findGroupById(Long aLong);

    List<Group> findAllGroupsByChildId(Long aLong);
}
