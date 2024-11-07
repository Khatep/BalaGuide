package org.khatep.balaguide.services;

import org.khatep.balaguide.exceptions.ChildNotEnrolledException;
import org.khatep.balaguide.exceptions.ChildNotFoundException;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;

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
