package de.marcel.monetenmanager.cli;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import de.marcel.monetenmanager.application.CategoryService;
import de.marcel.monetenmanager.domain.Category;
import de.marcel.monetenmanager.domain.CategoryType;

public class CategoryCLIHandler {

    private final CategoryService service;
    private final Scanner scanner;

    public CategoryCLIHandler(CategoryService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void handleCreateCategory(UUID userId) {
        try {
            System.out.print("Name der Kategorie: ");
            String name = scanner.nextLine();

            System.out.print("Typ (EINNAHME/AUSGABE): ");
            CategoryType type = CategoryType.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Farbe (z. B. blau, gruen): ");
            String color = scanner.nextLine();

            service.createCategory(userId, name, type, color);
            System.out.println("✅ Kategorie gespeichert.");

        } catch (Exception e) {
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
                System.out.printf("[%s] %s (%s) [%s]\n",
                        c.getId(), c.getName(), c.getType(), c.getColor());
            }
        }
    }
}
