package kz.balaguide.child_module.repository;

import kz.balaguide.common_module.core.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    /**
     * Retrieves all {@link Child} heirs associated with a given parent ID.
     *
     * @param parentId the ID of the parent whose children are to be retrieved
     * @return a {@link List} of {@link Child} heirs associated with the specified parent ID
     */
    @Query("SELECT c FROM Child c WHERE c.parent.id = :parentId")
    List<Child> findAllByParentId(Long parentId);

    Optional<Child> findByPhoneNumber(String phoneNumber);

}
