package com.example.tacocloud.controller;

import com.example.tacocloud.model.Ingredient;
import com.example.tacocloud.model.IngredientType;
import com.example.tacocloud.model.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    @GetMapping
    public String showDesignForm(Model model) {
        setIngredientsToModel(model);

        model.addAttribute("design", new Taco());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design,
                                Errors errors, Model model) {
        if (errors.hasErrors()) {
            setIngredientsToModel(model);
            model.addAttribute("design", design);
            return "design";
        }

        log.info("Processing design " + design);

        return "redirect:/orders/current";
    }

    private void setIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", IngredientType.WRAP),
                new Ingredient("COTO", "Corn Tortilla", IngredientType.WRAP),
                new Ingredient("GRBF", "Ground Beef", IngredientType.PROTEIN),
                new Ingredient("CARN", "Carnitas", IngredientType.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", IngredientType.VEGGIES),
                new Ingredient("LETC", "Lettuce", IngredientType.VEGGIES),
                new Ingredient("CHED", "Cheddar", IngredientType.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", IngredientType.CHEESE),
                new Ingredient("SLSA", "Salsa", IngredientType.SAUCE),
                new Ingredient("SRCR", "Sour Cream", IngredientType.SAUCE)
        );

        IngredientType[] types = IngredientType.values();
        for (IngredientType type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    ingredients.stream().filter(x -> x.getType().equals(type))
                            .collect(Collectors.toList()));
        }
    }
}
