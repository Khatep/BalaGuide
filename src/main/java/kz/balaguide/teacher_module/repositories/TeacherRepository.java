package kz.balaguide.teacher_module.repositories;

import kz.balaguide.common_module.core.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = """
        SELECT * FROM teachers
        WHERE education_center_id = :educationCenterId
    """, nativeQuery = true)
    List<Teacher> findAllByEducationCenterId(Long educationCenterId);
}
