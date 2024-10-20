package org.khatep.balaguide.dao.impl;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.dao.ParentDao;
import org.khatep.balaguide.models.entities.Parent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ParentDaoImpl implements ParentDao {

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Parent> parentRowMapper = (rs, rowNum) -> Parent.builder()
            .id(rs.getLong("id"))
            .firstName(rs.getString("first_name"))
            .lastName(rs.getString("last_name"))
            .phoneNumber(rs.getString("phone_number"))
            .email(rs.getString("email"))
            .password(rs.getString("password"))
            .address(rs.getString("address"))
            .balance(rs.getBigDecimal("balance"))
            .build();

    @Override
    public int save(Parent parent) {
        String sql = "INSERT INTO parent(first_name, last_name, phone_number, email, password, address, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                parent.getFirstName(),
                parent.getLastName(),
                parent.getPhoneNumber(),
                parent.getEmail(),
                parent.getPassword(),
                parent.getAddress(),
                parent.getBalance()
        );
    }

    @Override
    public int update(Parent parent) {
        String sql = "UPDATE parent SET first_name = ?, last_name = ?, phone_number = ?, email = ?, password = ?, address = ?, balance = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                parent.getFirstName(),
                parent.getLastName(),
                parent.getPhoneNumber(),
                parent.getEmail(),
                parent.getPassword(),
                parent.getAddress(),
                parent.getBalance(),
                parent.getId()
        );
    }

    @Override
    public int deleteById(Long parentId) {
        String sql = "DELETE FROM parent WHERE id = ?";
        return jdbcTemplate.update(sql, parentId);
    }

    @Override
    public List<Parent> findAll() {
        String sql = "SELECT * FROM parent";
        return jdbcTemplate.query(sql, parentRowMapper);
    }

    @Override
    public Optional<Parent> findById(Long parentId) {
        String sql = "SELECT * FROM parent WHERE id = ?";
        return jdbcTemplate.query(sql, parentRowMapper, parentId).stream().findFirst();
    }

    @Override
    public Optional<Parent> findByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM parent WHERE phone_number = ?";
        return jdbcTemplate.query(sql, parentRowMapper, phoneNumber).stream().findFirst();
    }
}
