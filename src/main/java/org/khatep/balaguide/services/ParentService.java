package org.khatep.balaguide.services;

import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.entities.Parent;

import java.util.List;
import java.util.function.Predicate;

public interface ParentService {

    /**
     * Registers a new parent and save to Database.
     *
     * @param parent The parent to be registered.
     * @return the number of affected rows.
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
     * Adds a new child to a parent's list of children and saves the child to the repository.
     *
     * @param child  The child to be added.
     * @param parentPassword The parent`s password for confirmation.
     * @return Child which was added.
     */
    Child addChild(Child child, String parentPassword);

    /**
     * Removes a child from a parent's list of children and deletes the child from the repository.
     *
     * @param parentPassword The parent`s password for confirmation.
     * @param childId  the number of affected rows.
     */
    boolean removeChild(Long childId, String parentPassword);

    /**
     * Retrieves a list of all children associated with a parent.
     *
     * @param parentId The id of parent whose children will be retrieved.
     * @return A list of children associated with the given parent.
     */
    List<Child> getMyChildren(Long parentId, String parentPassword);

    /**
     * Searches for courses by name or a portion of the name.
     *
     * @param query The search term to match against course names.
     * @return A list of courses whose names contain the search term, case-insensitive.
     */
    List<Course> searchCourses(String query);

    /**
     * Searches for courses by name or a portion of the name, then filters and sorts the results.
     *
     * @param query     The search term to match against course names.
     * @param predicate A predicate to filter the courses.
     * @return A list of filtered and sorted courses whose names contain the search term.
     */
    List<Course> searchCoursesWithFilter(String query, Predicate<Course> predicate);

    /**
     * Enrolls a child in a course after verifying that the child belongs to the parent.
     *
     * @param parentId The id of parent of the child.
     * @param childId  The id of child to be enrolled.
     * @param courseId The id of course.
     * @return True if the child is successfully enrolled, otherwise false.
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
     * Processes payment for enrolling a child in a course.
     *
     * @param parentId  The id of parent making the payment.
     * @param course  The course being paid for.
     * @return True if the payment is successful and the child's enrollment is confirmed, otherwise false.
     */
    boolean payForCourse(Long parentId, Course course);

    /**
     * Adds a specified amount of money to the balance of a parent.
     *
     * @param parentId the ID of the parent whose balance is to be updated
     * @param amountOfMoney the amount of money to add to the parent's balance
     * @return a message indicating the result of the operation, such as "Balance updated successfully" or an error message
     */
    String addBalance(Long parentId, Integer amountOfMoney);
}
