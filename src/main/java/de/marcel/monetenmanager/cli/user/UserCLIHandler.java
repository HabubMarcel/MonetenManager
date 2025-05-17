package de.marcel.monetenmanager.cli.user;

import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marcel.monetenmanager.application.category.CategoryService;
import de.marcel.monetenmanager.application.user.UserLoginService;
import de.marcel.monetenmanager.application.user.UserRegistrationService;
import de.marcel.monetenmanager.cli.category.CategoryCLIHandler;
import de.marcel.monetenmanager.domain.category.CategoryType;
import de.marcel.monetenmanager.domain.user.User;

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

    public void handleRegistration(CategoryService categoryService) {
    try {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("E-Mail: ");
        String email = scanner.nextLine();

        System.out.print("Passwort: ");
        String password = scanner.nextLine();

        UUID userId = registrationService.registerUser(name, email, password);
        System.out.println("✅ Benutzer registriert mit ID: " + userId);
        System.out.println("\n👉 Hinweis: Kategorien sind wichtig, um Transaktionen und Budgets zu verwalten.");

        System.out.println("""
        📦 Möchtest du mit folgenden Standardkategorien starten?

            - Gehalt (EINNAHME)
            - Lebensmittel (AUSGABE)
            - Miete (AUSGABE)
            - Sparen: Urlaub (AUSGABE, Sparziel)
            - Sparen: Auto (AUSGABE, Sparziel)
        """);
        System.out.print("(j/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("j")) {
            categoryService.createCategory(userId, "Gehalt", CategoryType.EINNAHME, "blau", false);
            categoryService.createCategory(userId, "Lebensmittel", CategoryType.AUSGABE, "grün", false);
            categoryService.createCategory(userId, "Miete", CategoryType.AUSGABE, "rot", false);
            categoryService.createCategory(userId, "Sparen: Urlaub", CategoryType.AUSGABE, "gelb", true);
            categoryService.createCategory(userId, "Sparen: Auto", CategoryType.AUSGABE, "orange", true);
            System.out.println("✅ Standardkategorien erstellt.");
        } else {
            System.out.println("\n✍️ Bitte erstelle jetzt mindestens 3 eigene Kategorien.");
            System.out.println("📌 Hinweis: Wähle eindeutige Namen – z. B. 'Miete', nicht mehrfach 'Kosten'.\n");

            for (int i = 1; i <= 3; i++) {
                System.out.println("Kategorie " + i + ":");
                new CategoryCLIHandler(categoryService, scanner).handleCreateCategory(userId);
                System.out.println();
            }
        }

    } catch (IllegalArgumentException ex) {
        log.error("❌ Fehler beim registrieren: {}", ex.getMessage());
        System.out.println("❌ Registrieren fehlgeschlagen.");
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
                    handleFirstTimeSetup(user.getId());
                    return user;
                })
                .orElseGet(() -> {
                    System.out.println("❌ Login fehlgeschlagen.");
                    return null;
                });
    }

    private void handleFirstTimeSetup(UUID userId) {
        // Prüfe, ob bereits Kategorien vorhanden sind
        if (categoryService.getCategoriesForUser(userId).isEmpty()) {
            System.out.println("🔔 Hinweis: Kategorien sind essenziell für Budgets & Transaktionen.");
            System.out.println("""
                📦 Möchtest du mit folgenden Standardkategorien starten?

                    - Gehalt (EINNAHME)
                    - Essen (AUSGABE)
                    - Sparen Urlaub (AUSGABE, Sparziel)
                    - Sparen Auto (AUSGABE, Sparziel)
                    - Nebenjob (EINNAHME)

                ℹ️ Hinweis: Gib eindeutige Namen – z. B. keine zwei Kategorien mit dem Namen „Essen“.
                """);
System.out.print("(j/n): ");

            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("j")) {
                categoryService.createCategory(userId, "Gehalt", CategoryType.EINNAHME, "grün", false);
                categoryService.createCategory(userId, "Essen", CategoryType.AUSGABE, "rot", false);
                categoryService.createCategory(userId, "Sparen Urlaub", CategoryType.AUSGABE, "blau", true);
                categoryService.createCategory(userId, "Sparen Auto", CategoryType.AUSGABE, "gelb", true);
                categoryService.createCategory(userId, "Nebenjob", CategoryType.EINNAHME, "grau", false);
                System.out.println("✅ Standardkategorien wurden erstellt.");
            } else {
                System.out.println("Bitte erstelle mindestens 3 eigene Kategorien:");
                for (int i = 1; i <= 3; i++) {
                    System.out.println("\nKategorie " + i + ":");
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Typ (EINNAHME/AUSGABE): ");
                    CategoryType type = CategoryType.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Farbe: ");
                    String color = scanner.nextLine();
                    System.out.print("Ist das eine Sparkategorie? (j/n): ");
                    boolean savings = scanner.nextLine().trim().equalsIgnoreCase("j");

                    categoryService.createCategory(userId, name, type, color, savings);
                }
                System.out.println("✅ Kategorien gespeichert.");
            }
        }
    }
}
