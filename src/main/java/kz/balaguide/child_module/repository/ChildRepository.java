package kz.balaguide.child_module.repository;

import kz.balaguide.common_module.core.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    @Query("SELECT c FROM Child c WHERE c.parent.id = :parentId")
    List<Child> findAllByParentId(Long parentId);

    Optional<Child> findByPhoneNumber(String phoneNumber);

    @Query(value = """
            SELECT c.* FROM children c
            JOIN child_group cg ON c.id = cg.child_id
            JOIN groups g ON g.id = cg.group_id
            JOIN courses cr ON g.course_id = cr.id
            WHERE cr.education_center_id = :centerId
            """, nativeQuery = true)
    List<Child> findAllByEducationCenterId(Long centerId);

}
