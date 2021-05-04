package com.example.tacocloud.controller;

import com.example.tacocloud.model.Ingredient;
import com.example.tacocloud.model.IngredientType;
import com.example.tacocloud.model.Order;
import com.example.tacocloud.model.Taco;
import com.example.tacocloud.repository.jpa.IngredientRepository;
import com.example.tacocloud.repository.jpa.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private IngredientRepository ingredientRepository;
    private TacoRepository tacoRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository,
                                TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @GetMapping
    public String showDesignForm(Model model) {
        setIngredientsToModel(model);

        model.addAttribute("design", new Taco());
        return "design";
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco taco() {
        return new Taco();
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design,
                                Errors errors, Model model,
                                @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            setIngredientsToModel(model);
            model.addAttribute("design", design);
            return "design";
        }

        log.info("Processing design " + design);
        Taco savedTaco = tacoRepository.save(design);
        order.addTaco(savedTaco);

        return "redirect:/orders/current";
    }

    private void setIngredientsToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        IngredientType[] types = IngredientType.values();
        for (IngredientType type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    ingredients.stream().filter(x -> x.getType().equals(type))
                            .collect(Collectors.toList()));
        }
    }
}
