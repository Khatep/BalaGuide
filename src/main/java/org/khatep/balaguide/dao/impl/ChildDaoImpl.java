package org.khatep.balaguide.dao.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.khatep.balaguide.dao.ChildDao;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.enums.Category;
import org.khatep.balaguide.models.enums.Gender;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@Repository
public class ChildDaoImpl implements ChildDao {

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Child> childRowMapper = (rs, rowNum) -> Child.builder()
            .id(rs.getLong("id"))
            .firstName(rs.getString("first_name"))
            .lastName(rs.getString("last_name"))
            .birthDate(rs.getDate("date_of_birth").toLocalDate())
            .phoneNumber(rs.getString("phone_number"))
            .password(rs.getString("password"))
            .gender(Gender.valueOf(rs.getString("gender")))
            .parentId(rs.getLong("parent_id"))
            .build();

    @Override
    public int save(Child child) {
        String sql = "INSERT INTO child (first_name, last_name, date_of_birth, phone_number, password, gender, parent_id) values (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                child.getFirstName(),
                child.getLastName(),
                child.getBirthDate(),
                child.getPhoneNumber(),
                child.getPassword(),
                child.getGender().name(),
                child.getParentId());
    }

    @Override
    public int update(Child child) {
        String sql = "UPDATE child SET first_name = ?, last_name = ?, date_of_birth = ?, phone_number = ?, password = ?";
        return jdbcTemplate.update(sql,
                child.getFirstName(),
                child.getLastName(),
                child.getBirthDate(),
                child.getPhoneNumber(),
                child.getPassword()
        );
    }

    @Override
    public int deleteById(Long childId) {
        String sql = "DELETE FROM child WHERE id = ?";
        return jdbcTemplate.update(sql, childId);
    }

    @Override
    public List<Child> findAll() {
        String sql = "SELECT * FROM child";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Child ch = new Child();
            ch.setId(rs.getLong("id"));
            ch.setFirstName(rs.getString("first_name"));
            ch.setLastName(rs.getString("last_name"));
            ch.setBirthDate(rs.getDate("date_of_birth").toLocalDate());
            ch.setPhoneNumber(rs.getString("phone_number"));
            ch.setPassword(rs.getString("password"));
            ch.setGender(Gender.valueOf(rs.getString("gender")));
            ch.setParentId(rs.getLong("parent_id"));
            return ch;
        });
    }

    @Override
    public List<Child> findAllByParentId(Long parentId) {
        String sql = "SELECT * FROM child WHERE parent_id = ?";
        return jdbcTemplate.query(sql, childRowMapper, parentId);
    }

    @Override
    public Optional<Child> findById(Long childId) {
        String sql = "SELECT * FROM child WHERE id = ?";
        return jdbcTemplate.query(sql, childRowMapper, childId).stream().findFirst();
    }

    @Override
    public List<Course> findCoursesByChildId(Long childId) {
        String sql = "SELECT * FROM child_course cc JOIN course c ON cc.course_id = c.id WHERE cc.child_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Course(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getLong("educational_center_id"),
                        Category.valueOf(rs.getString("category")),
                        rs.getString("age_range"),
                        rs.getBigDecimal("price"),
                        rs.getInt("durability"),
                        rs.getString("address"),
                        rs.getInt("max_participants"),
                        rs.getInt("current_participants"),
                        Collections.singletonList(rs.getString("course_materials"))
                ), childId);
    }

    @Override
    public Optional<Child> findByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM child WHERE phone_number = ?";
        return jdbcTemplate.query(sql, childRowMapper, phoneNumber).stream().findFirst();
    }
}
