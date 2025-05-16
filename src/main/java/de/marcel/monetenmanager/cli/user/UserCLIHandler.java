package de.marcel.monetenmanager.cli.user;

import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marcel.monetenmanager.application.user.UserLoginService;
import de.marcel.monetenmanager.application.user.UserRegistrationService;
import de.marcel.monetenmanager.domain.user.User;

public class UserCLIHandler {
    private final UserRegistrationService registrationService;
    private final UserLoginService loginService;
    private final Scanner scanner;
    private static final Logger log = LoggerFactory.getLogger(UserCLIHandler.class);
    

    public UserCLIHandler(UserRegistrationService registrationService, UserLoginService loginService, Scanner scanner) {
        this.registrationService = registrationService;
        this.loginService = loginService;
        this.scanner = scanner;
    }

    public void handleRegistration() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("E-Mail: ");
            String email = scanner.nextLine();

            System.out.print("Passwort: ");
            String password = scanner.nextLine();

            UUID userId = registrationService.registerUser(name, email, password);
            System.out.println("✅ Benutzer registriert mit ID: " + userId);
        } catch (IllegalArgumentException ex) {
            log.error("❌ Fehler beim registrieren: {}", ex.getMessage());
            System.out.println("❌ Registrieren Fehlgeschlagen");
        }
    }

    public User handleLogin() {
        System.out.print("E-Mail: ");
        String email = scanner.nextLine();

        System.out.print("Passwort: ");
        String password = scanner.nextLine();

        return loginService.login(email, password)
                .map(user -> {
                    System.out.println("✅ Login erfolgreich. Willkommen, " + user.getName() + "!");
                    return user;
                })
                .orElseGet(() -> {
                    System.out.println("❌ Login fehlgeschlagen.");
                    return null;
                });
    }
}
