package kz.balaguide.course_module.services;

import kz.balaguide.course_module.dto.EnrollmentActionDto;

public interface GroupService {
    boolean unenrollChild(EnrollmentActionDto enrollmentActionDto);
}
