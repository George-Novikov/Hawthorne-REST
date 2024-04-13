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
                @Tag(name = OpenApi.USER_TAG, description = "User: save, get, delete, list, count, bulk activation"),
        }
)
public class OpenApi {
        public static final String AUTH_TAG = "auth";
        public static final String DOCUMENT_TAG = "document";
        public static final String FILE_TAG = "file";
        public static final String SETTINGS_TAG = "settings";
        public static final String USER_TAG = "user";

        /**
         * auth
         */
        public static final String REGISTRATION_DESCRIPTION = """
                Registration requires minimal user information: login, password, first name, last name. A nickname is optional. </br>
                The registration result depends on the settings: </br>
                - immediately returns JWT access token if isAuthRequired = false </br>
                - otherwise returns a message asking you to contact your admin for activation if isManualUserActivation = true </br>
                - or returns a message about successful registration — this allows you to independently activate user account via /api/v1/auth/activate/{userID}
                """;

        public static final String LOGIN_DESCRIPTION = """
                Login and password should be passed as body parameters via x-www-form-urlencoded.
                """;

        public static final String ACTIVATION_DESCRIPTION = """
                Allows to activate registered user by id.
                """;

        /**
         * document
         */
        public static final String SAVE_DOCUMENT_DESCRIPTION = """
                Save the complete document object. </br>
                If the document id is present, the existing document will be updated. </br>
                Each document can be saved as a draft (with empty fields) and filled later.
                """;

        public static final String GET_DOCUMENT_DESCRIPTION = """
                Get the complete document object by id.
                """;

        public static final String DELETE_DOCUMENT_DESCRIPTION = """
                Delete a document permanently by id.
                """;

        public static final String LIST_DOCUMENTS_DESCRIPTION = """
                The limit and offset parameters are always used and set by 0 by default — a request without setting the limit will result in an empty array. </br>
                So it's best to call /count endpoint first then calculate the desired output size to implement pagination.
                """;

        public static final String COUNT_DOCUMENTS_DESCRIPTION = """
                Count the number of documents in the storage.
                """;

        /**
         * file
         */
        public static final String SAVE_FILE_DESCRIPTION = """
                Save the complete file object. </br>
                If the file id is present, the existing file will be updated. </br>
                The request requires a formData body.
                """;

        public static final String GET_FILE_METADATA_DESCRIPTION = """
                Get file metadata by id.
                """;

        public static final String GET_BINARY_FILE_DESCRIPTION = """
                Get file binary data by id. </br>
                The Content-Type header is designed to change dynamically, depending on the MIME type of the stored file.
                """;

        public static final String DELETE_FILE_DESCRIPTION = """
                Delete a file permanently by id.
                """;

        public static final String LIST_FILES_DESCRIPTION = """
                The limit and offset parameters are always used and set by 0 by default — a request without setting the limit will result in an empty array. </br>
                So it's best to call /count endpoint first then calculate the desired output size to implement pagination.
                """;

        public static final String COUNT_FILES_DESCRIPTION = """
                Count the number of files in the storage.
                """;

        /**
         * settings
         */
        public static final String SAVE_SETTINGS_DESCRIPTION = """
                Save settings object. </br>
                An id is not required since it's marked as @SingletonEntity — a special entity type of the Hawthorne library.
                """;

        public static final String GET_SETTINGS_DESCRIPTION = """
                Get the complete settings object.
                """;

        /**
         * user
         */
        public static final String USER_SAVE_DESCRIPTION = """
                Save the complete user object. </br>
                If the user id is present, the existing user will be updated. </br>
                The login is checked for uniqueness for new users.
                """;

        public static final String USER_GET_DESCRIPTION = """
                Get the complete user object. </br>
                You can equally use the id or login parameters. </br>
                If the id is passed, no login is required and vice versa.
                """;
        public static final String USER_DELETE_DESCRIPTION = """
                Delete a user permanently. </br>
                You can equally use the id or login parameters. </br>
                If the id is passed, no login is required and vice versa.
                """;

        public static final String LIST_USERS_DESCRIPTION = """
                The limit and offset parameters are always used and set by 0 by default — a request without setting the limit will result in an empty array. </br>
                So it's best to call /count endpoint first then calculate the desired output size to implement pagination
                """;

        public static final String COUNT_USERS_DESCRIPTION = """
                Count the number of users in the storage.
                """;

        public static final String GET_ACTIVATION_LIST_DESCRIPTION = """
                Get a list of user ids that are currently blocked
                """;

        public static final String BULK_ACTIVATION_DESCRIPTION = """
                Unlike /api/v1/auth/activate/{userID} endpoint this one requires the ADMIN role. </br>
                This endpoint consumes an JSON object with an array of blocked user ids, which can be retrieved via GET /api/v1/user/activationList. </br>
                However, the response will contain two arrays: activatedIDs (representing N of successful operations) and notFoundIDs (respectively, unsuccessful ones).
                """;

}
