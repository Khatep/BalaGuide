package org.khatep.balaguide.repositories;

import org.khatep.balaguide.models.entities.EducationCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EducationCenterRepository extends JpaRepository<EducationCenter, Long> {
    /**
     * Retrieves an {@link EducationCenter} entity by its email.
     *
     * @param email the email of the education center to be retrieved
     * @return an {@link Optional} containing the {@link EducationCenter} if found, or empty if not found
     */
    Optional<EducationCenter> findByEmail(String email);
}
