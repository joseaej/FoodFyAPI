package com.FoodFyAPI.FoodFyAPI.controllers;

import com.FoodFyAPI.FoodFyAPI.domain.models.Recipe;
import com.FoodFyAPI.FoodFyAPI.domain.repository.RecipeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecipeController {
    private final RecipeRepository recipeRepository = new RecipeRepository();
    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes() throws JsonProcessingException {
        return  recipeRepository.getAllRecipes();
    };
    @GetMapping("/recipes/title={name}")
    public List<Recipe> getRecipesByName(@PathVariable String name) throws JsonProcessingException {
        return  recipeRepository.getRecipesByName(name);
    };

}
