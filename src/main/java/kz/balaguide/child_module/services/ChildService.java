package kz.balaguide.child_module.services;

import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChildService {

    Page<Child> findAll(int page, int size);

    Child findById(Long id);
    Child findByPhoneNumber(String phoneNumber);

    Child save(Child child);

    Child update(Long id, Child child);

    void removeChild(Long childId);

    List<Course> getMyCourses(Child child);

    boolean enrollChildToCourse(EnrollmentActionDto enrollmentActionDto);

    boolean unenrollChildFromCourse(EnrollmentActionDto enrollmentActionDto);

    boolean markAttendanceFromQr(Long childId, Long lessonId);
}
