package kz.balaguide.services.child;

import kz.balaguide.core.exceptions.buisnesslogic.generic.ChildNotEnrolledToCourseException;
import kz.balaguide.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.core.entities.Child;
import kz.balaguide.core.entities.Course;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ChildService {

    Page<Child> findAll(int page, int size);

    Child findById(Long id);

    Child save(Child child);

    Child update(Long id, Child child);

    void removeChild(Long childId);

    List<Course> getMyCourses(Child child);

}
