package org.khatep.balaguide.services;

import org.khatep.balaguide.exceptions.CourseFullException;
import org.khatep.balaguide.exceptions.IneligibleChildException;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;

import java.util.List;

public interface CourseService {

    /**
     * Adds a new course to the system.
     *
     * @param course the {@link Course} entity to be added
     * @return the saved {@link Course} entity
     */
    Course addCourse(Course course);

    /**
     * Updates the information of an existing course.
     *
     * @param courseId the ID of the course to update
     * @param updatedCourse the {@link Course} entity containing updated information
     * @return the updated {@link Course} entity
     * @throws RuntimeException if the course is not found
     */
    Course updateInformation(Long courseId, Course updatedCourse);

    /**
     * Retrieves a list of all courses available in the system.
     *
     * @return a {@link List} of {@link Course} entities
     */
    List<Course> getCourses();

    /**
     * Enrolls a child in a specified course.
     *
     * @param courseId the ID of the course to enroll the child in
     * @param childId the ID of the child to enroll
     * @return true if the enrollment is successful
     * @throws CourseFullException if the course is full
     * @throws IneligibleChildException if the child is not eligible for the course
     * @throws RuntimeException if the course or child is not found
     */
    boolean enrollChild(Long courseId, Long childId);

    /**
     * Unenrolls a child from a specified course.
     *
     * @param courseId the ID of the course to unenroll the child from
     * @param childId the ID of the child to unenroll
     * @return true if the unenrollment is successful
     * @throws RuntimeException if the course or child is not found, or if the child is not enrolled in any courses
     */
    boolean unenrollChild(Long courseId, Long childId);

    /**
     * Checks if a course is full.
     *
     * @param course the {@link Course} entity to check
     * @return true if the current number of participants is equal to or exceeds the maximum allowed
     */
    boolean isCourseFull(Course course);

    /**
     * Retrieves the current number of participants in a course.
     *
     * @param course the {@link Course} entity to check
     * @return the current number of participants
     * @throws RuntimeException if the course is not found
     */
    int getCurrentParticipants(Course course);

    /**
     * Checks if a child is eligible for a specified course based on age range.
     *
     * @param course the {@link Course} entity to check against
     * @param child the {@link Child} entity to verify eligibility
     * @return true if the child is eligible for the course
     */
    boolean isChildEligible(Course course, Child child);
}
