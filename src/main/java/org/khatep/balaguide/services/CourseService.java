package org.khatep.balaguide.services;

import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    /**
     * Adds a new course to the repository.
     *
     * @param course the {@link Course} to be added
     * @return the number of affected rows
     */
    Course addCourse(Course course);

    /**
     * Updates the information of an existing course.
     *
     * @param courseId the ID of the course to be updated
     * @param updatedCourse the {@link Course} object containing updated information
     * @return the number of affected rows
     */
    Course updateInformation(Long courseId, Course updatedCourse);

    /**
     * Retrieves a list of all available courses.
     *
     * @return a list of {@link Course} objects
     */
    List<Course> getCourses();

    /**
     * Adds a child as a participant in a specified course.
     *
     * @param courseId the ID of the course
     * @param childId the ID of the child to be added
     * @return true if the child is successfully added; false otherwise
     */
    boolean enrollChild(Long courseId, Long childId);

    /**
     * Removes a child as a participant from a specified course.
     *
     * @param courseId the ID of the course
     * @param childId the ID of the child to be removed
     * @return true if the child is successfully removed; false otherwise
     */
    boolean removeParticipant(Long courseId, Long childId);

    /**
     * Checks if a given course is full.
     *
     * @param course the {@link Course} to check
     * @return true if the course is full; false otherwise
     */
    boolean isCourseFull(Course course);

    /**
     * Gets the current number of participants in a specified course.
     *
     * @param courseId the ID of the course
     * @return the number of participants, or -1 if the course is not found
     */
    int getCurrentParticipants(Long courseId);

    /**
     * Checks if a child is eligible for a course based on age range.
     *
     * @param course the {@link Course} to check against
     * @param child the {@link Child} to check for eligibility
     * @return true if the child is eligible; false otherwise
     */
    boolean isChildEligible(Course course, Child child);
}
