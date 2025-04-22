package com.FoodFyAPI.FoodFyAPI.controllers;

import com.FoodFyAPI.FoodFyAPI.domain.models.Recipe;
import com.FoodFyAPI.FoodFyAPI.domain.repository.RecipeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@SecurityRequirement(name = "BearerAuth")
public class RecipeController {

    private final RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestHeader("Authorization") String bearerToken)
            throws JsonProcessingException {

        String token = bearerToken.replace("Bearer ", "");
        List<Recipe> recipes = recipeRepository.getAllRecipes(token);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> getRecipesByName(
            @RequestParam String name,
            @RequestHeader("Authorization") String bearerToken) throws JsonProcessingException {

        String token = bearerToken.replace("Bearer ", "");
        List<Recipe> recipes = recipeRepository.getRecipeByName(name, token);
        return ResponseEntity.ok(recipes);
    }
}
