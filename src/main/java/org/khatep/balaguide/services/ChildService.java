package org.khatep.balaguide.services;

import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;

import java.util.List;

public interface ChildService {
    
    /**
     * Retrieves a list of courses that the specified child is enrolled in.
     *
     * @param child the {@link Child} entity whose courses are to be retrieved
     * @return a list of {@link Course} entities the child is enrolled in
     */
    List<Course> getMyCourses(Child child);

    /**
     * Handles the login functionality for a child.
     *
     * @param build the {@link Child} entity containing login credentials
     * @return true if login is successful; false otherwise
     */
    boolean login(Child build);
}
