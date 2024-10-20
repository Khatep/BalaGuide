package org.khatep.balaguide.dao.impl;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.dao.EducationCenterDao;
import org.khatep.balaguide.models.entities.EducationCenter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class EducationCenterDaoImpl implements EducationCenterDao {

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<EducationCenter> educationCenterRowMapper = (rs, rowNum) -> EducationCenter.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .dateOfCreated(rs.getDate("date_of_created").toLocalDate())
            .phoneNumber(rs.getString("phone_number"))
            .address(rs.getString("address"))
            .instagramLink(rs.getString("instagram_link"))
            .build();

    @Override
    public int save(EducationCenter educationCenter) {
        String sql = "INSERT INTO education_center (name, date_of_created, phone_number, address, instagram_link) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                educationCenter.getName(),
                educationCenter.getDateOfCreated(),
                educationCenter.getPhoneNumber(),
                educationCenter.getAddress(),
                educationCenter.getInstagramLink());
    }

    @Override
    public int update(EducationCenter educationCenter) {
        String sql = "UPDATE education_center SET name = ?, date_of_created = ?, phone_number = ?, address = ?, instagram_link = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                educationCenter.getName(),
                educationCenter.getDateOfCreated(),
                educationCenter.getPhoneNumber(),
                educationCenter.getAddress(),
                educationCenter.getInstagramLink(),
                educationCenter.getId());
    }

    @Override
    public int deleteById(Long educationCenterId) {
        String sql = "DELETE FROM education_center WHERE id = ?";
        return jdbcTemplate.update(sql, educationCenterId);
    }

    @Override
    public List<EducationCenter> findAll() {
        String sql = "SELECT * FROM education_center";
        return jdbcTemplate.query(sql, educationCenterRowMapper);
    }

    @Override
    public Optional<EducationCenter> findById(Long educationCenterId) {
        String sql = "SELECT * FROM education_center WHERE id = ?";
        return jdbcTemplate.query(sql, educationCenterRowMapper, educationCenterId).stream().findFirst();
    }
}
