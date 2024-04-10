package com.georgen.hawthornerest.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(title = "Hawthorne web object storage REST API documentation.", version = "v1"),
        tags = {
                @Tag(name = "auth", description = "Registration & authentication"),
                @Tag(name = "user", description = "User"),
        }
)
public class OpenApi {}
