package com.example.tacocloud.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Taco {

    @NotNull(message = "Name must be at least 5 characters long")
    @Size(min=5, message = "Name must be at least 5 characters long")
    private String name;

    @NotNull(message = "You must choose at least 1 ingredient")
    @Size(min=1, message = "You must choose at least 1 ingredient")
    private List<String> ingredients;

}
