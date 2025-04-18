package com.FoodFyAPI.FoodFyAPI.domain.repository;

import com.FoodFyAPI.FoodFyAPI.domain.models.Recipe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

public class RecipeRepository {
    private static HttpEntity<String> getStringHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ1Y2JoZ2ZvZW5ydWdjY2ltZnZ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQwNDIzNDYsImV4cCI6MjA1OTYxODM0Nn0.1GUjkZ2DYzCxYyfW_iutrOhIrXeIodyLucAnUczWoJA");
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ1Y2JoZ2ZvZW5ydWdjY2ltZnZ2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQwNDIzNDYsImV4cCI6MjA1OTYxODM0Nn0.1GUjkZ2DYzCxYyfW_iutrOhIrXeIodyLucAnUczWoJA");

        return new HttpEntity<>(headers);
    }

    public List<Recipe> getAllRecipes() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entity = getStringHttpEntity();

        ResponseEntity<String> response = restTemplate.exchange(
                "https://rucbhgfoenrugccimfvv.supabase.co/rest/v1/recipes",
                HttpMethod.GET,
                entity,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), new TypeReference<List<Recipe>>(){});
    }
    public List<Recipe> getRecipesByName(String name) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entity = getStringHttpEntity();
        String url = String.format("https://rucbhgfoenrugccimfvv.supabase.co/rest/v1/recipes?title=eq.%s&select=*",name);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), new TypeReference<List<Recipe>>(){});
    }
    public List<Recipe> getRecipeByServingCocking(String time) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entity = getStringHttpEntity();
        String url = String.format("https://rucbhgfoenrugccimfvv.supabase.co/rest/v1/recipes?coocking_time=eq.%s&select=*",time);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), new TypeReference<List<Recipe>>(){});
    }
}
