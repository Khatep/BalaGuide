package org.khatep.balaguide.dao;

import org.khatep.balaguide.models.entities.EducationCenter;

import java.util.List;
import java.util.Optional;

public interface EducationCenterDao {

    /**
     * Saves a new {@link EducationCenter} entity to the database.
     *
     * @param educationCenter the {@link EducationCenter} entity to be saved
     * @return the number of affected rows
     */
    int save(EducationCenter educationCenter);

    /**
     * Updates an existing {@link EducationCenter} entity in the database.
     *
     * @param educationCenter the {@link EducationCenter} entity to be updated
     * @return the number of affected rows
     */
    int update(EducationCenter educationCenter);

    /**
     * Deletes an {@link EducationCenter} entity from the database by its ID.
     *
     * @param educationCenterId the ID of the {@link EducationCenter} entity to be deleted
     * @return the number of affected rows
     */
    int deleteById(Long educationCenterId);

    /**
     * Retrieves all {@link EducationCenter} entities from the database.
     *
     * @return a list of all {@link EducationCenter} entities
     */
    List<EducationCenter> findAll();

    /**
     * Retrieves an {@link EducationCenter} entity by its ID.
     *
     * @param educationCenterId the ID of the {@link EducationCenter} entity to be retrieved
     * @return an {@link Optional} containing the found {@link EducationCenter}, or empty if not found
     */
    Optional<EducationCenter> findById(Long educationCenterId);
}
