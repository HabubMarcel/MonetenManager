package de.marcel.monetenmanager.cli.user;

import java.io.Console;
import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marcel.monetenmanager.cli.category.CategoryCLIHandler;
import de.marcel.monetenmanager.domain.category.CategoryColor;
import de.marcel.monetenmanager.domain.category.CategoryName;
import de.marcel.monetenmanager.domain.category.CategoryType;
import de.marcel.monetenmanager.domain.user.User;
import de.marcel.monetenmanager.service.category.CategoryService;
import de.marcel.monetenmanager.service.user.UserLoginService;
import de.marcel.monetenmanager.service.user.UserRegistrationService;

public class UserCLIHandler {
    private final UserRegistrationService registrationService;
    private final UserLoginService loginService;
    private final CategoryService categoryService;
    private final Scanner scanner;
    private static final Logger log = LoggerFactory.getLogger(UserCLIHandler.class);

    public UserCLIHandler(UserRegistrationService registrationService,
                          UserLoginService loginService,
                          CategoryService categoryService,
                          Scanner scanner) {
        this.registrationService = registrationService;
        this.loginService = loginService;
        this.categoryService = categoryService;
        this.scanner = scanner;
    }

    public void handleRegistration() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("E-Mail: ");
            String email = scanner.nextLine();

            String password = promptPassword();

            UUID userId = registrationService.registerUser(name, email, password);
            System.out.println("Benutzer registriert mit ID: " + userId);
            System.out.println("\n Hinweis: Kategorien sind wichtig, um Transaktionen und Budgets zu verwalten.");

            System.out.println("""
              Möchtest du mit folgenden Standardkategorien starten?

                - Gehalt (EINNAHME)
                - Lebensmittel (AUSGABE)
                - Miete (AUSGABE)
                - Sparen: Urlaub (AUSGABE, Sparziel)
                - Sparen: Auto (AUSGABE, Sparziel)
            """);
            System.out.print("(j/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("j")) {
                createDefaultCategories(userId);
            } else {
                System.out.println("\n Bitte erstelle jetzt mindestens 3 eigene Kategorien.");
                System.out.println("Hinweis: Wähle eindeutige Namen -z.B. 'Miete', nicht mehrfach 'Kosten'.\n");

                for (int i = 1; i <= 3; i++) {
                    System.out.println("Kategorie " + i + ":");
                    new CategoryCLIHandler(categoryService, scanner).handleCreateCategory(userId);
                    System.out.println();
                }
            }

        } catch (IllegalArgumentException ex) {
            log.error("X Fehler beim Registrieren: {}", ex.getMessage());
            System.out.println("X Registrieren fehlgeschlagen.");
        }
    }

    public User handleLogin() {
        try {
            System.out.print("E-Mail: ");
            String emailInput = scanner.nextLine();

            System.out.print("Passwort: ");
            String password = scanner.nextLine();

            return loginService.login(emailInput, password)
                    .map(user -> {
                        log.info("Login erfolgreich für Benutzer: {}", user.getEmail());
                        System.out.println("Login erfolgreich. Willkommen, " + user.getName() + "!");
                        handleFirstTimeSetup(user.getId());
                        return user;
                    })
                    .orElseGet(() -> {
                        log.warn("Login fehlgeschlagen für E-Mail: {}", emailInput);
                        System.out.println("X Login fehlgeschlagen.");
                        return null;
                    });

        } catch (IllegalArgumentException ex) {
            log.error("Ungültige Eingabe beim Login: {}", ex.getMessage());
            System.out.println("X Ungültige Eingaben: " + ex.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Technischer Fehler beim Login", e);
            System.out.println("X Ein unerwarteter Fehler ist aufgetreten.");
            return null;
        }
    }


    private void handleFirstTimeSetup(UUID userId) {
        if (categoryService.getCategoriesForUser(userId).isEmpty()) {
            System.out.println("Hinweis: Kategorien sind essenziell für Budgets & Transaktionen.");
            System.out.println("""
                  Möchtest du mit folgenden Standardkategorien starten?

                    - Gehalt (EINNAHME)
                    - Essen (AUSGABE)
                    - Sparen Urlaub (AUSGABE, Sparziel)
                    - Sparen Auto (AUSGABE, Sparziel)
                    - Nebenjob (EINNAHME)

                   Hinweis: Gib eindeutige Namen -z.B. keine zwei Kategorien mit dem Namen „Essen“.
            """);
            System.out.print("(j/n): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("j")) {
                createDefaultCategories(userId);
            } else {
                System.out.println("Bitte erstelle mindestens 3 eigene Kategorien:");
                for (int i = 1; i <= 3; i++) {
                    System.out.println("\nKategorie " + i + ":");
                    System.out.print("Name: ");
                    CategoryName name = new CategoryName(scanner.nextLine());
                    System.out.print("Typ (EINNAHME/AUSGABE): ");
                    CategoryType type = CategoryType.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Farbe: ");
                    CategoryColor color = new CategoryColor(scanner.nextLine());
                    System.out.print("Ist das eine Sparkategorie? (j/n): ");
                    boolean savings = scanner.nextLine().trim().equalsIgnoreCase("j");

                    categoryService.createCategory(userId, name, type, color, savings);
                }
                System.out.println("Kategorien gespeichert.");
            }
        }
    }

    private void createDefaultCategories(UUID userId) {
        categoryService.createCategory(userId, new CategoryName("Gehalt"), CategoryType.EINNAHME, new CategoryColor("gruen"), false);
        categoryService.createCategory(userId, new CategoryName("Essen"), CategoryType.AUSGABE, new CategoryColor("rot"), false);
        categoryService.createCategory(userId, new CategoryName("Sparen Urlaub"), CategoryType.AUSGABE, new CategoryColor("blau"), true);
        categoryService.createCategory(userId, new CategoryName("Sparen Auto"), CategoryType.AUSGABE, new CategoryColor("gelb"), true);
        categoryService.createCategory(userId, new CategoryName("Nebenjob"), CategoryType.EINNAHME, new CategoryColor("grau"), false);
        System.out.println("Standardkategorien wurden erstellt.");
    }

    private String promptPassword() {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword("Passwort: ");
            return new String(passwordChars);
        } else {
            System.out.print("Passwort (! sichtbar): ");
            return scanner.nextLine();
        }
    }
}
