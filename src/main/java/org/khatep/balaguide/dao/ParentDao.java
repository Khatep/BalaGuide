package org.khatep.balaguide.dao;

import org.khatep.balaguide.models.entities.Parent;

import java.util.List;
import java.util.Optional;

public interface ParentDao {

    /**
     * Saves a new {@link Parent} entity to the database.
     *
     * @param parent the {@link Parent} entity to be saved
     * @return the number of affected rows
     */
    int save(Parent parent);

    /**
     * Updates an existing {@link Parent} entity in the database.
     *
     * @param parent the {@link Parent} entity to be updated
     * @return the number of affected rows
     */
    int update(Parent parent);

    /**
     * Deletes a {@link Parent} entity from the database by its ID.
     *
     * @param parentId the ID of the {@link Parent} entity to be deleted
     * @return the number of affected rows
     */
    int deleteById(Long parentId);

    /**
     * Retrieves all {@link Parent} entities from the database.
     *
     * @return a list of all {@link Parent} entities
     */
    List<Parent> findAll();

    /**
     * Retrieves a {@link Parent} entity by its ID.
     *
     * @param parentId the ID of the {@link Parent} entity to be retrieved
     * @return an {@link Optional} containing the found {@link Parent}, or empty if not found
     */
    Optional<Parent> findById(Long parentId);

    /**
     * Retrieves a {@link Parent} entity by its phone number.
     *
     * @param phoneNumber the phone number of the {@link Parent} entity to be retrieved
     * @return an {@link Optional} containing the found {@link Parent}, or empty if not found
     */
    Optional<Parent> findByPhoneNumber(String phoneNumber);
}
