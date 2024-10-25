package org.khatep.balaguide.repositories;

import org.khatep.balaguide.models.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
