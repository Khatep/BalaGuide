package org.khatep.balaguide.dao;

import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;

import java.util.List;
import java.util.Optional;

public interface ChildDao {

    /**
     * Saves a new {@link Child} entity to the database.
     *
     * @param child the {@link Child} entity to be saved
     * @return the number of affected rows
     */
    int save(Child child);

    /**
     * Updates an existing {@link Child} entity in the database.
     *
     * @param child the {@link Child} entity to be updated
     * @return the number of affected rows
     */
    int update(Child child);

    /**
     * Deletes a {@link Child} entity from the database by its ID.
     *
     * @param childId the ID of the {@link Child} entity to be deleted
     * @return the number of affected rows
     */
    int deleteById(Long childId);

    /**
     * Retrieves all {@link Child} entities from the database.
     *
     * @return a list of all {@link Child} entities
     */
    List<Child> findAll();

    /**
     * Retrieves all {@link Child} entities associated with a specific parent ID.
     *
     * @param parentId the ID of the parent whose children are to be retrieved
     * @return a list of {@link Child} entities associated with the specified parent
     */
    List<Child> findAllByParentId(Long parentId);

    /**
     * Retrieves a {@link Child} entity by its ID.
     *
     * @param childId the ID of the {@link Child} entity to be retrieved
     * @return an {@link Optional} containing the found {@link Child}, or empty if not found
     */
    Optional<Child> findById(Long childId);

    /**
     * Retrieves a list of {@link Course} entities associated with a specific child ID.
     *
     * @param childId the ID of the child whose courses are to be retrieved
     * @return a list of {@link Course} entities associated with the specified child
     */
    List<Course> findCoursesByChildId(Long childId);

    /**
     * Retrieves a {@link Child} entity by its phone number.
     *
     * @param phoneNumber the phone number of the {@link Child} entity to be retrieved
     * @return an {@link Optional} containing the found {@link Child}, or empty if not found
     */
    Optional<Child> findByPhoneNumber(String phoneNumber);
}
