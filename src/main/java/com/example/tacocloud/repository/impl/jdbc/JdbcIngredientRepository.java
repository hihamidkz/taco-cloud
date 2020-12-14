package com.example.tacocloud.repository.impl.jdbc;

import com.example.tacocloud.model.Ingredient;
import com.example.tacocloud.model.IngredientType;
import com.example.tacocloud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private static final String SELECT_ALL = "select id, name, type from Ingredient";
    private static final String SELECT_BY_ID = "select id, name, type from Ingredient where id = ?";
    private static final String INSERT = "insert into Ingredient (id, name, type) values (?, ?, ?)";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRowToIngredient);
    }

    @Override
    public Ingredient findById(String id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRowToIngredient, id);
    }

    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update(INSERT, ingredient.getId(),
                ingredient.getName(), ingredient.getType().toString());

        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                IngredientType.valueOf(rs.getString("type")));
    }
}
