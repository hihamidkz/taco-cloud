package com.example.tacocloud.repository.jdbc;

import com.example.tacocloud.model.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();
    Ingredient findById(String id);
    Ingredient save(Ingredient ingredient);

}
