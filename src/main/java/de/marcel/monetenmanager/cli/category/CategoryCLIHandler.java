package de.marcel.monetenmanager.cli.category;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marcel.monetenmanager.cli.transaction.TransactionCLIHandler;
import de.marcel.monetenmanager.domain.category.Category;
import de.marcel.monetenmanager.domain.category.CategoryColor;
import de.marcel.monetenmanager.domain.category.CategoryName;
import de.marcel.monetenmanager.domain.category.CategoryType;
import de.marcel.monetenmanager.service.category.CategoryService;
;;

public class CategoryCLIHandler {
    private static final Logger log = LoggerFactory.getLogger(TransactionCLIHandler.class);
    private final CategoryService categoryService;
    private final Scanner scanner;

    public CategoryCLIHandler(CategoryService service, Scanner scanner) {
        this.categoryService = service;
        this.scanner = scanner;
    }

    public void handleCreateCategory(UUID userId) {
        try {
            System.out.println("Aktuelle Kategorien:");
            List<Category> existing = categoryService.getCategoriesForUser(userId);
            if (existing.isEmpty()) {
                System.out.println("Keine vorhanden.");
            } else {
                existing.forEach(c ->
                    System.out.printf("→ %s (%s, %s)%s\n",
                        c.getName(),
                        c.getType(),
                        c.getColor(),
                        c.isSavings() ? " [€ Sparziel]" : "")
                );
            }
            System.out.println("!Bitte vermeide doppelte Namen!");

            System.out.print("Name der Kategorie: ");
            String name = scanner.nextLine();
            CategoryName nameObj = new CategoryName(name);

            System.out.print("Typ (EINNAHME/AUSGABE): ");
            CategoryType type = CategoryType.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Farbe (z.B. blau, grün): ");
            String color = scanner.nextLine();
            CategoryColor colorObj = new CategoryColor(color);

            System.out.print("Ist dies ein Sparziel? (j/n): ");
            String savingsInput = scanner.nextLine().trim().toLowerCase();
            boolean isSavings = savingsInput.startsWith("j");

            categoryService.createCategory(userId, nameObj, type, colorObj, isSavings);
            System.out.println("Kategorie gespeichert.");

        } catch (Exception e) {
            log.error("X Fehler beim Erstellen des Budgets", e);
            System.out.println("X Fehler beim Erstellen des Budgets");
        }
    }

    public void handleListCategories(UUID userId) {
        List<Category> categories = categoryService.getCategoriesForUser(userId);

        if (categories.isEmpty()) {
            System.out.println("Noch keine Kategorien vorhanden.");
        } else {
            System.out.println("Kategorien:");
            for (Category c : categories) {
                System.out.printf("[%s] %s (%s) [%s]%s\n",
                        c.getId(),
                        c.getName(),
                        c.getType(),
                        c.getColor(),
                        c.isSavings() ? " [Sparziel]" : ""
                );
            }
        }
    }
}
