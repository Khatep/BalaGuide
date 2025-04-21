package kz.balaguide.teacher_module.repositories;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kz.balaguide.common_module.core.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByEmail(@NotNull(message = "Email must be not null") @NotBlank(message = "Email must be not empty") @Email(message = "Email must be in format: user@example.com") String email);
    boolean existsByPhoneNumber(@NotNull(message = "Phone number must be not null") @NotBlank(message = "Phone number must be not empty") String phoneNumber);
}
