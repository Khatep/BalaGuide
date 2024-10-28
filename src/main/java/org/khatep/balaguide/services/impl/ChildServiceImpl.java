package org.khatep.balaguide.services.impl;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.aop.annotations.ForLog;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.repositories.ChildRepository;
import org.khatep.balaguide.repositories.CourseRepository;
import org.khatep.balaguide.services.ChildService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChildServiceImpl implements ChildService {

    private final ChildRepository childRepository;
    private final CourseRepository courseRepository;

    /**
     * Retrieves a list of courses that the specified child is enrolled in.
     *
     * @param child the {@link Child} entity for which to retrieve enrolled courses
     * @return a {@link List} of {@link Course} entities that the child is enrolled in
     * @throws RuntimeException if the child is not found or if the child is not enrolled in any courses
     */
    @Override
    @ForLog
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<Course> getMyCourses(Child child) {
        Optional<Child> childFromDatabase = childRepository.findById(child.getId());

        if (childFromDatabase.isEmpty())
            throw new RuntimeException("Child not found with ID: " + child.getId());

        Optional<List<Course>> enrolledCoursesFromDatabase = childFromDatabase.map(childEntity ->
                courseRepository.findAllByChildId(childFromDatabase.get().getId())
        );

        if (enrolledCoursesFromDatabase.isEmpty())
            throw new RuntimeException("This child is not enrolled in any courses");

        return enrolledCoursesFromDatabase.get();
    }

    /**
     * Logs in a child by verifying their phone number and password.
     *
     * @param child the {@link Child} entity containing the phone number and password for authentication
     * @return true if the login is successful
     * @throws RuntimeException if the child is not found or if the password is incorrect
     */
    @Override
    @ForLog
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean login(Child child) {
        Optional<Child> childFromDatabase = childRepository.findByPhoneNumber(child.getPhoneNumber());

        if (childFromDatabase.isEmpty())
            throw new RuntimeException("Child not found with phone number: " + child.getPhoneNumber());

        if (!childFromDatabase.get().getPassword().contentEquals(child.getPassword()))
            throw new RuntimeException("Wrong child password with ID: " + child.getId());

        return true;
    }
}
