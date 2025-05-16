package de.marcel.monetenmanager.cli.budget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marcel.monetenmanager.application.budget.BudgetService;
import de.marcel.monetenmanager.application.category.CategoryService;
import de.marcel.monetenmanager.domain.budget.Budget;
import de.marcel.monetenmanager.domain.category.Category;

public class BudgetCLIHandler {

    private final CategoryService categoryService;
    private final BudgetService service;
    private final Scanner scanner;
    private static final Logger log = LoggerFactory.getLogger(BudgetCLIHandler.class);

public BudgetCLIHandler(BudgetService service, CategoryService categoryService, Scanner scanner) {
    this.service = service;
    this.categoryService = categoryService;
    this.scanner = scanner;
}

    public void handleCreateBudget(UUID userId) {
        try {
            System.out.print("Name des Budgets: ");
            String name = scanner.nextLine();

            System.out.print("Kategorie-Name: ");
            String categoryName = scanner.nextLine();

            List<Category> categories = categoryService.getCategoriesForUser(userId).stream()
            .filter(c -> c.getName().equalsIgnoreCase(categoryName))
            .toList();

            if (categories.isEmpty()) {
            System.out.println("❌ Keine Kategorie mit diesem Namen gefunden.");
            return;
            }
            if (categories.size() > 1) {
            System.out.println("⚠️ Mehrere Kategorien mit dem Namen gefunden. Bitte ID verwenden.");
            for (Category c : categories) {
            System.out.printf("→ [%s] %s (%s, %s)\n", c.getId(), c.getName(), c.getType(), c.getColor());
        }
    return;
}

UUID categoryId = categories.get(0).getId();

            System.out.print("Betrag (z. B. 100.00): ");
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            System.out.print("Startdatum (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Enddatum (YYYY-MM-DD): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());

            service.createBudget(userId, categoryId, name, amount, startDate, endDate);
            log.info("Budget gespeichert: Name={}, Betrag={}, Zeitraum={} bis {}, Kategorie-ID={}",
            name, amount, startDate, endDate, categoryId);
            System.out.println("✅ Budget gespeichert.");

        } catch (Exception e) {
            log.error("❌ Fehler beim Erstellen des Budgets", e);
            System.out.println("❌ Budget konnte nicht gespeichert werden.");
        }
    }

    public void handleListBudgets(UUID userId) {
        List<Budget> budgets = service.getBudgetsForUser(userId);
        log.info("Gefundene Budgets für userId={}: {}", userId, budgets.size());

        if (budgets.isEmpty()) {
            System.out.println("ℹ️ Noch keine Budgets vorhanden.");
        } else {
            System.out.println("📊 Budgets:");
            for (Budget b : budgets) {
                System.out.printf("[%s] %s: %.2f € (%s bis %s, Kategorie-ID: %s)\n",
                        b.getId(), b.getName(), b.getAmount(), b.getStartDate(), b.getEndDate(), b.getCategoryId());
            }
        }
    }
}
