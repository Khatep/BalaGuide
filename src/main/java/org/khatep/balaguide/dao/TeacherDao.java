package org.khatep.balaguide.dao;

import org.khatep.balaguide.models.entities.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherDao {

    /**
     * Saves a new {@link Teacher} entity to the database.
     *
     * @param teacher the {@link Teacher} entity to be saved
     * @return the number of affected rows
     */
    int save(Teacher teacher);

    /**
     * Updates an existing {@link Teacher} entity in the database.
     *
     * @param teacher the {@link Teacher} entity to be updated
     * @return the number of affected rows
     */
    int update(Teacher teacher);

    /**
     * Deletes a {@link Teacher} entity from the database by its ID.
     *
     * @param teacherId the ID of the {@link Teacher} entity to be deleted
     * @return the number of affected rows
     */
    int deleteById(Long teacherId);

    /**
     * Retrieves all {@link Teacher} entities from the database.
     *
     * @return a list of all {@link Teacher} entities
     */
    List<Teacher> findAll();

    /**
     * Retrieves a {@link Teacher} entity by its ID.
     *
     * @param teacherId the ID of the {@link Teacher} entity to be retrieved
     * @return an {@link Optional} containing the found {@link Teacher}, or empty if not found
     */
    Optional<Teacher> findById(Long teacherId);
}
