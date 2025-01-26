package kz.balaguide.services.child;

import kz.balaguide.core.exceptions.buisnesslogic.generic.ChildNotEnrolledException;
import kz.balaguide.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.core.entities.Child;
import kz.balaguide.core.entities.Course;

import java.util.List;
import java.util.Optional;

public interface ChildService {

    List<Child> findAll();

    Optional<Child> findById(Long id);

    Child save(Child child);

    Optional<Child> update(Long id, Child child);

    boolean removeChild(Long childId);

    List<Course> getMyCourses(Child child) throws ChildNotFoundException, ChildNotEnrolledException;

}
