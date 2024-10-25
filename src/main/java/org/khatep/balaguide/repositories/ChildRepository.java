package org.khatep.balaguide.repositories;

import org.khatep.balaguide.models.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {

    @Query("SELECT c FROM Child c WHERE c.parent.id = :parentId")
    List<Child> findAllByParentId(Long parentId);

    /**
     * Retrieves a {@link Child} entity by its phone number.
     *
     * @param phoneNumber the phone number of the {@link Child} entity to be retrieved
     * @return an {@link Optional} containing the found {@link Child}, or empty if not found
     */
    Optional<Child> findByPhoneNumber(String phoneNumber);
}
