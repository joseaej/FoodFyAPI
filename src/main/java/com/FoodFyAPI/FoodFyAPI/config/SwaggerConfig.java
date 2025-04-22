package com.FoodFyAPI.FoodFyAPI.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "FoodFyAPI",
                description = "The best food API that you ever see",
                contact = @Contact(
                        name = "joseaej",
                        url = "https://github.com/joseaej"
                )
        )
)
@SecurityScheme(
        name = "BearerAuth",
        description = "Insert your JWT for Supabase on 'Authorize' field like that: Bearer {token}",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
