package com.FoodFyAPI.FoodFyAPI.domain.repository;

import com.FoodFyAPI.FoodFyAPI.domain.models.Recipe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RecipeRepository {

    @Value("${supabase.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private HttpEntity<String> createEntityWithToken(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", apiKey);
        headers.set("Authorization", "Bearer " + bearerToken);
        return new HttpEntity<>(headers);
    }

    public List<Recipe> getAllRecipes(String bearerToken) throws JsonProcessingException {
        HttpEntity<String> entity = createEntityWithToken(bearerToken);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://rucbhgfoenrugccimfvv.supabase.co/rest/v1/recipes",
                HttpMethod.GET,
                entity,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), new TypeReference<List<Recipe>>() {});
    }

    public List<Recipe> getRecipeByName(String name, String bearerToken) throws JsonProcessingException {
        HttpEntity<String> entity = createEntityWithToken(bearerToken);
        String encodedName = name.replace(" ", "%20");
        String url = "https://rucbhgfoenrugccimfvv.supabase.co/rest/v1/recipes?select=*&title=eq." + encodedName;

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), new TypeReference<List<Recipe>>() {});
    }
}
