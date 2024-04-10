package com.georgen.hawthornerest.services;

import com.georgen.hawthornerest.model.auth.AuthResponse;
import com.georgen.hawthornerest.model.auth.RegistrationRequest;
import com.georgen.hawthornerest.model.exceptions.AuthException;
import com.georgen.hawthornerest.model.exceptions.UserException;
import com.georgen.hawthornerest.model.system.Settings;
import com.georgen.hawthornerest.model.users.User;
import com.georgen.hawthornerest.security.JwtHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.georgen.hawthornerest.model.messages.AuthMessage.*;

@Service
public class AuthenticationService {
    private AuthenticationManager authManager;
    private UserService userService;
    private JwtHandler jwtHandler;
    private PasswordEncoder passwordEncoder;
    private SettingsService settingsService;

    public AuthenticationService(AuthenticationManager authManager,
                                 UserService userService,
                                 JwtHandler jwtHandler,
                                 PasswordEncoder passwordEncoder,
                                 SettingsService settingsService) {
        this.authManager = authManager;
        this.userService = userService;
        this.jwtHandler = jwtHandler;
        this.passwordEncoder = passwordEncoder;
        this.settingsService = settingsService;
    }

    public AuthResponse register(RegistrationRequest request) throws Exception {
        if (request.isEmpty()) throw new AuthException();
        if (userService.isLoginTaken(request.getLogin())) throw new UserException("This login is already in use.");

        User user = new User(
                request.getLogin(),
                passwordEncoder.encode(request.getPassword()),
                request.getFirstname(),
                request.getLastname(),
                request.getNickname()
        );

        userService.save(user);

        Settings settings = settingsService.get();
        boolean isAuthRequired = settings.isAuthRequired();
        boolean isActivatedManually = settings.isManualUserActivation();

        if (isAuthRequired){
            return new AuthResponse(isActivatedManually ? CONTACT_ADMIN_RESPONSE : ADDITIONAL_INFO_RESPONSE);
        } else {
            String token = jwtHandler.generateToken(user);
            return new AuthResponse(token);
        }
    }

    public AuthResponse login(String login, String password) throws Exception {
        if (login.isEmpty() || password.isEmpty()) throw new AuthException();

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password)
        );

        User user = userService
                .getOptionalByLogin(login)
                .orElseThrow(AuthException::new);

        String token = jwtHandler.generateToken(user);
        return new AuthResponse(token);
    }
}
