package com.georgen.hawthornerest.controllers.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(title = "Hawthorne web object storage REST API documentation.", version = "v1"),
        tags = {
                @Tag(name = OpenApi.AUTH_TAG, description = "User registration, authentication, activation"),
                @Tag(name = OpenApi.DOCUMENT_TAG, description = "Document: save, get, delete, list, count"),
                @Tag(name = OpenApi.FILE_TAG, description = "File: save, get metadata, get binary, delete, list, count"),
                @Tag(name = OpenApi.SETTINGS_TAG, description = "Settings: save, get"),
                @Tag(name = OpenApi.USER_TAG, description = "User: save, get, delete, list, count"),
        }
)
public class OpenApi {
        public static final String AUTH_TAG = "auth";
        public static final String DOCUMENT_TAG = "document";
        public static final String FILE_TAG = "file";
        public static final String SETTINGS_TAG = "settings";
        public static final String USER_TAG = "user";


        public static final String USER_SAVE_DESCRIPTION = """
                        Save the complete user object.
                        If the user id is present, the existing user will be updated.
                        The login is checked for uniqueness for new users.
                        """;

        public static final String USER_GET_DESCRIPTION = """
                        Get the complete user object.
                        You can equally use the id or login parameters.
                        If the id is passed, no login is required and vice versa.
                        """;
        public static final String USER_DELETE_DESCRIPTION = """
                        Delete a user permanently.
                        You can equally use the id or login parameters.
                        If the id is passed, no login is required and vice versa.
                        """;
}
