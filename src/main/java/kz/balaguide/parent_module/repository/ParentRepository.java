package kz.balaguide.parent_module.repository;

import kz.balaguide.common_module.core.entities.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    /**
     * Retrieves a {@link Parent} entity by its phone number.
     *
     * @param phoneNumber the phone number of the {@link Parent} entity to be retrieved
     * @return an {@link Optional} containing the found {@link Parent}, or empty if not found
     */
    Optional<Parent> findByPhoneNumber(String phoneNumber);

    /**
     * Retrieves a {@link Parent} entity by its email.
     *
     * @param email the email of the {@link Parent} entity to be retrieved
     * @return an {@link Optional} containing the found {@link Parent}, or empty if not found
     */
    Optional<Parent> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
