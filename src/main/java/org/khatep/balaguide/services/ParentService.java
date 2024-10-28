package org.khatep.balaguide.services;

import org.khatep.balaguide.exceptions.ChildNotBelongToParentException;
import org.khatep.balaguide.exceptions.InsufficientFundsException;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.entities.Parent;

import java.util.List;
import java.util.function.Predicate;

public interface ParentService {

    /**
     * Registers a new parent in the system.
     *
     * @param parent the {@link Parent} entity to be registered
     * @return the saved {@link Parent} entity
     */
    Parent signUp(Parent parent);

    /**
     * Logs in a parent by checking their credentials.
     *
     * @param parent The parent attempting to log in, with their ID, phone number, and password.
     * @return True if the login credentials are valid, otherwise false.
     */
    boolean login(Parent parent);

    /**
     * Adds a child to the parent's account.
     *
     * @param child the {@link Child} entity to be added
     * @param parentPassword the parent's password for verification
     * @return the saved {@link Child} entity
     * @throws IllegalArgumentException if the parent is not found or the password is incorrect
     */
    Child addChild(Child child, String parentPassword);

    /**
     * Removes a child from the parent's account.
     *
     * @param childId the ID of the {@link Child} entity to be removed
     * @param parentPassword the parent's password for verification
     * @return true if the child was successfully removed
     * @throws IllegalArgumentException if the child or parent is not found or the password is incorrect
     */
    boolean removeChild(Long childId, String parentPassword);

    /**
     * Retrieves a list of children associated with the parent.
     *
     * @param parentId the ID of the {@link Parent} entity
     * @param parentPassword the parent's password for verification
     * @return a {@link List} of {@link Child} entities associated with the parent
     * @throws IllegalArgumentException if the parent is not found or the password is incorrect
     */
    List<Child> getMyChildren(Long parentId, String parentPassword);

    /**
     * Searches for courses with a specific filter and sorting.
     *
     * @param query the search query string
     * @param predicate the {@link Predicate} to apply as a filter
     * @return a {@link List} of {@link Course} entities that match the filter and query
     */
    List<Course> searchCoursesWithFilter(String query, Predicate<Course> predicate);

    /**
     * Enrolls a child in a specified course and processes payment.
     *
     * @param parentId the ID of the {@link Parent} entity
     * @param childId the ID of the {@link Child} entity to enroll
     * @param courseId the ID of the {@link Course} entity
     * @return true if enrollment is successful
     * @throws ChildNotBelongToParentException if the child does not belong to the specified parent
     * @throws InsufficientFundsException if the payment fails due to insufficient funds
     */
    boolean enrollChildToCourse(Long parentId, Long childId, Long courseId);

    /**
     * Unenrolls a child from a specific course.
     *
     * @param parentId The id of the parent
     * @param courseId The ID of the course.
     * @param childId  The ID of the child to be unenrolled.
     * @return True if the child is successfully unenrolled, otherwise false.
     */
    boolean unenrollChildFromCourse(Long parentId, Long courseId, Long childId);

    /**
     * Processes payment for a course.
     *
     * @param parentId the ID of the {@link Parent} entity making the payment
     * @param course the {@link Course} entity for which payment is being made
     * @return true if the payment is successful
     * @throws InsufficientFundsException if the parent has insufficient funds
     */
    boolean payForCourse(Long parentId, Course course);

    /**
     * Adds balance to the parent's account.
     *
     * @param parentId the ID of the {@link Parent} entity
     * @param amountOfMoney the amount to add to the balance
     * @return a success message indicating the updated balance
     * @throws IllegalArgumentException if the parent is not found
     */
    String addBalance(Long parentId, Integer amountOfMoney);
}
