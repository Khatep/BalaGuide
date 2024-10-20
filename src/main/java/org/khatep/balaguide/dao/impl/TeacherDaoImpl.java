package org.khatep.balaguide.dao.impl;

import org.khatep.balaguide.dao.TeacherDao;
import org.khatep.balaguide.models.entities.Teacher;
import org.khatep.balaguide.models.enums.Gender;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Repository
@RequiredArgsConstructor
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Teacher> teacherRowMapper = (rs, rowNum) -> Teacher.builder()
            .id(rs.getLong("id"))
            .firstName(rs.getString("first_name"))
            .lastName(rs.getString("last_name"))
            .dateOfBirth(rs.getDate("date_of_birth").toLocalDate())
            .phoneNumber(rs.getString("phone_number"))
            .password(rs.getString("password"))
            .salary(rs.getBigDecimal("salary"))
            .gender(Gender.valueOf(rs.getString("gender")))
            .build();

    @Override
    public int save(Teacher teacher) {
        String sql = "INSERT INTO teacher (first_name, last_name, date_of_birth, phone_number, password, salary, gender) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getDateOfBirth(),
                teacher.getPhoneNumber(),
                teacher.getPassword(),
                teacher.getSalary(),
                teacher.getGender().name()
        );
    }

    @Override
    public int update(Teacher teacher) {
        String sql = "UPDATE teacher SET first_name = ?, last_name = ?, date_of_birth = ?, phone_number = ?, password = ?, salary = ?, gender = ? " +
                "WHERE id = ?";
        return jdbcTemplate.update(sql,
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getDateOfBirth(),
                teacher.getPhoneNumber(),
                teacher.getPassword(),
                teacher.getSalary(),
                teacher.getGender().name(),
                teacher.getId()
        );
    }

    @Override
    public int deleteById(Long teacherId) {
        String sql = "DELETE FROM teacher WHERE id = ?";
        return jdbcTemplate.update(sql, teacherId);
    }

    @Override
    public List<Teacher> findAll() {
        String sql = "SELECT * FROM teacher";
        return jdbcTemplate.query(sql, teacherRowMapper);
    }

    @Override
    public Optional<Teacher> findById(Long teacherId) {
        String sql = "SELECT * FROM teacher WHERE id = ?";
        return jdbcTemplate.query(sql, teacherRowMapper, teacherId).stream().findFirst();
    }
}
