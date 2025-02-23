package kz.balaguide.education_center_module.repository;

import kz.balaguide.common_module.core.entities.EducationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationCenterRepository extends JpaRepository<EducationCenter, Long> {
    /**
     * Retrieves an {@link EducationCenter} entity by its email.
     *
     * @param email the email of the education center to be retrieved
     * @return an {@link Optional} containing the {@link EducationCenter} if found, or empty if not found
     */
    Optional<EducationCenter> findByEmail(String email);
    Optional<EducationCenter> findByPhoneNumber(String phoneNumber);
}
