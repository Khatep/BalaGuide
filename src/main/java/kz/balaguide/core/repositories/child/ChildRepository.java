package kz.balaguide.core.repositories.child;

import kz.balaguide.core.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {

    /**
     * Retrieves all {@link Child} entities associated with a given parent ID.
     *
     * @param parentId the ID of the parent whose children are to be retrieved
     * @return a {@link List} of {@link Child} entities associated with the specified parent ID
     */
    @Query("SELECT c FROM Child c WHERE c.parent.id = :parentId")
    List<Child> findAllByParentId(Long parentId);

}
