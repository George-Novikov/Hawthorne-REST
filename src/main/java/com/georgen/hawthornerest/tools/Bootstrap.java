package com.georgen.hawthornerest.tools;

import com.georgen.hawthorne.api.Repository;
import com.georgen.hawthornerest.model.messages.SystemMessage;
import com.georgen.hawthornerest.model.users.Role;
import com.georgen.hawthornerest.model.users.User;
import com.georgen.hawthornerest.services.SettingsService;
import com.georgen.hawthornerest.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Bootstrap implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    @Value("${admin-user.login}")
    private String adminLogin;
    @Value("${admin-user.password}")
    private String adminPassword;

    private SettingsService settingsService;
    private UserService userService;

    public Bootstrap(SettingsService settingsService, UserService userService) {
        this.settingsService = settingsService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            printGreeting();
            ensureAdminInitialization();
            printIsAuthRequired();
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void printGreeting(){
        String message =
                """
                \n
                -------------------------------------------------
                Welcome to the Hawthorne REST API object storage.
                For more information, visit https://github.com/George-Novikov/Hawthorne-DB/blob/master/README.md
                -------------------------------------------------
                """;

        LOGGER.info(message);
    }

    private void printIsAuthRequired() throws Exception {
        boolean isAuthRequired = settingsService.get().isAuthRequired();
        if (isAuthRequired){
            LOGGER.warn(SystemMessage.AUTH_REQUIRED.getDescription());
        } else {
            LOGGER.info(SystemMessage.NO_AUTH_REQUIRED.getDescription());
        }
    }

    private void ensureAdminInitialization() throws Exception {
        User admin = userService.getByLogin(adminLogin);

        if (admin == null){
            admin = new User();
            admin.setLogin(adminLogin);
            admin.setPassword(adminPassword);
            admin.setFirstname("Admin");
            admin.setLastname("Admin");
            admin.setNickname("Admin");
            admin.setRole(Role.ADMIN);
            admin.setExpired(false);
            admin.setBlocked(false);

            userService.save(admin);
            LOGGER.warn("The admin user has been initialized.");
        }
    }
}
