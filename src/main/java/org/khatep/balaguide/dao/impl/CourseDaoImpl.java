package org.khatep.balaguide.dao.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.khatep.balaguide.dao.CourseDao;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.enums.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@Repository
public class CourseDaoImpl implements CourseDao {

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Course> courseRowMapper = (rs, rowNum) -> Course.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .description(rs.getString("description"))
            .educationCenterId(rs.getLong("educational_center_id"))
            .category(Category.valueOf(rs.getString("category")))
            .ageRange(rs.getString("age_range"))
            .price(rs.getBigDecimal("price"))
            .durability(rs.getInt("durability"))
            .address(rs.getString("address"))
            .maxParticipants(rs.getInt("max_participants"))
            .currentParticipants(rs.getInt("current_participants"))
            .courseMaterials(Arrays.asList(rs.getString("course_materials").split(","))) // Assuming materials are comma-separated
            .build();

    @Override
    public int save(Course course) {
        String sql = "INSERT INTO course (name, description, educational_center_id, category, age_range, price, durability, address, max_participants, current_participants, course_materials) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                course.getName(),
                course.getDescription(),
                course.getEducationCenterId(),
                course.getCategory().name(),
                course.getAgeRange(),
                course.getPrice(),
                course.getDurability(),
                course.getAddress(),
                course.getMaxParticipants(),
                course.getCurrentParticipants(),
                String.join(",", course.getCourseMaterials())); // Convert List to String
    }

    @Override
    public int update(Course course) {
        String sql = "UPDATE course SET name = ?, description = ?, educational_center_id = ?, category = ?, age_range = ?, price = ?, durability = ?, address = ?, max_participants = ?, current_participants = ?, course_materials = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                course.getName(),
                course.getDescription(),
                course.getEducationCenterId(),
                course.getCategory().name(),
                course.getAgeRange(),
                course.getPrice(),
                course.getDurability(),
                course.getAddress(),
                course.getMaxParticipants(),
                course.getCurrentParticipants(),
                String.join(",", course.getCourseMaterials()), // Convert List to String
                course.getId());
    }

    @Override
    public int deleteById(Long courseId) {
        String sql = "DELETE FROM course WHERE id = ?";
        return jdbcTemplate.update(sql, courseId);
    }

    @Override
    public List<Course> findAll() {
        String sql = "SELECT * FROM course";
        return jdbcTemplate.query(sql, courseRowMapper);
    }

    @Override
    public Optional<Course> findById(Long courseId) {
        String sql = "SELECT * FROM course WHERE id = ?";
        return jdbcTemplate.query(sql, courseRowMapper, courseId).stream().findFirst();
    }

    @Override
    public List<Course> findByNameContainingIgnoreCase(String courseName) {
        String sql = "SELECT * FROM course WHERE LOWER(name) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, courseRowMapper, "%" + courseName + "%");
    }

    @Override
    public int addParticipant(Long childId, Long courseId) {
        String sql = "INSERT INTO child_course (child_id, course_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, childId, courseId);
    }

}

