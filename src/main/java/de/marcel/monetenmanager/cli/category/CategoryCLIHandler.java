package de.marcel.monetenmanager.cli.category;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marcel.monetenmanager.application.category.CategoryService;
import de.marcel.monetenmanager.domain.category.Category;
import de.marcel.monetenmanager.domain.category.CategoryType;

public class CategoryCLIHandler {

    private final CategoryService service;
    private final Scanner scanner;
    private static final Logger log = LoggerFactory.getLogger(CategoryCLIHandler.class);

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
            log.info("Neue Kategorie erstellt: Name={}, Typ={}, Farbe={}", name, type, color);
            System.out.println("✅ Kategorie gespeichert.");

        } catch (Exception e) {
            log.error("❌ Fehler beim Erstellen der Kategorie", e);
            System.out.println("❌ Kategorie konnte nicht erstellt werden.");
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
