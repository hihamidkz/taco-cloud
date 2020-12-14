package com.example.tacocloud.repository.impl.jdbc;

import com.example.tacocloud.model.Taco;
import com.example.tacocloud.repository.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTaco(taco);
        taco.setId(tacoId);
        for (String ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }

        return taco;
    }

    private long saveTaco(Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)", Types.VARCHAR, Types.TIMESTAMP);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(taco.getName(),
                new Timestamp(taco.getCreatedAt().getTime())));

        pscf.setGeneratedKeysColumnNames("id");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(String ingredientId, long tacoId) {
        jdbcTemplate.update("insert into Taco_Ingredient (tacoId, ingredientId) " +
                "values (?, ?)", tacoId, ingredientId);
    }
}
