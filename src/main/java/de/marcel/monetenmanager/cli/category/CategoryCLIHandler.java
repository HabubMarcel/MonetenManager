package de.marcel.monetenmanager.cli.category;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marcel.monetenmanager.application.category.CategoryService;
import de.marcel.monetenmanager.cli.transaction.TransactionCLIHandler;
import de.marcel.monetenmanager.domain.category.Category;
import de.marcel.monetenmanager.domain.category.CategoryType;

public class CategoryCLIHandler {
    private static final Logger log = LoggerFactory.getLogger(TransactionCLIHandler.class);
    private final CategoryService service;
    private final Scanner scanner;

    public CategoryCLIHandler(CategoryService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void handleCreateCategory(UUID userId) {
        try {
            System.out.println("ℹ️ Aktuelle Kategorien:");
            List<Category> existing = service.getCategoriesForUser(userId);
            if (existing.isEmpty()) {
                System.out.println("Keine vorhanden.");
            } else {
                existing.forEach(c ->
                    System.out.printf("→ %s (%s, %s)%s\n",
                        c.getName(),
                        c.getType(),
                        c.getColor(),
                        c.isSavings() ? " [💰 Sparziel]" : "")
                );
            }
            System.out.println("⚠️ Bitte vermeide doppelte Namen!");

            System.out.print("Name der Kategorie: ");
            String name = scanner.nextLine();

            System.out.print("Typ (EINNAHME/AUSGABE): ");
            CategoryType type = CategoryType.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Farbe (z. B. blau, grün): ");
            String color = scanner.nextLine();

            System.out.print("Ist dies ein Sparziel? (j/n): ");
            String savingsInput = scanner.nextLine().trim().toLowerCase();
            boolean isSavings = savingsInput.startsWith("j");

            service.createCategory(userId, name, type, color, isSavings);
            System.out.println("✅ Kategorie gespeichert.");

        } catch (Exception e) {
            log.error("❌ Fehler beim Erstellen des Budgets", e);
            System.out.println("❌ Fehler: " + e.getMessage());
        }
    }

    public void handleListCategories(UUID userId) {
        List<Category> categories = service.getCategoriesForUser(userId);

        if (categories.isEmpty()) {
            System.out.println("ℹ️ Noch keine Kategorien vorhanden.");
        } else {
            System.out.println("📂 Kategorien:");
            for (Category c : categories) {
                System.out.printf("[%s] %s (%s) [%s]%s\n",
                        c.getId(),
                        c.getName(),
                        c.getType(),
                        c.getColor(),
                        c.isSavings() ? " [💰 Sparziel]" : ""
                );
            }
        }
    }
}
