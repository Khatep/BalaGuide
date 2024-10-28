package org.khatep.balaguide.services;

import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;

import java.util.List;

public interface ChildService {

    /**
     * Retrieves a list of courses that the specified child is enrolled in.
     *
     * @param child the {@link Child} entity for which to retrieve enrolled courses
     * @return a {@link List} of {@link Course} entities that the child is enrolled in
     * @throws RuntimeException if the child is not found or if the child is not enrolled in any courses
     */
    List<Course> getMyCourses(Child child);

    /**
     * Logs in a child by verifying their phone number and password.
     *
     * @param child the {@link Child} entity containing the phone number and password for authentication
     * @return true if the login is successful
     * @throws RuntimeException if the child is not found or if the password is incorrect
     */
    boolean login(Child child);
}
