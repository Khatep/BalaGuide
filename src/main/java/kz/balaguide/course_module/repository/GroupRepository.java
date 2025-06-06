package kz.balaguide.course_module.repository;

import kz.balaguide.common_module.core.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("""
            SELECT g FROM Group g
            JOIN g.childrenEnrolled c
            WHERE c.id = :childId
            """)
    List<Group> findAllByChildId(Long childId);

    @Modifying
    @Query(value = """
            DELETE FROM child_group
            WHERE child_id = :childId AND group_id = :groupId
            """, nativeQuery = true)
    void unenrollChildFromCourseGroup(@Param("childId") Long childId, @Param("groupId") Long groupId);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
            FROM child_group
            WHERE child_id = :childId AND group_id = :groupId
            """, nativeQuery = true)
    boolean isChildEnrolledInCourseGroup(@Param("groupId") Long groupId, @Param("childId") Long childId);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
            FROM groups
            WHERE course_id = :courseId AND name = :name
            """, nativeQuery = true)
    boolean isGroupExistsInCourseByName(String name, Long courseId);
}
