package de.marcel.monetenmanager.cli;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import de.marcel.monetenmanager.application.BudgetService;
import de.marcel.monetenmanager.application.CategoryService;
import de.marcel.monetenmanager.domain.Budget;
import de.marcel.monetenmanager.domain.Category;

public class BudgetCLIHandler {

    private final CategoryService categoryService;
    private final BudgetService service;
    private final Scanner scanner;

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
            System.out.println("✅ Budget gespeichert.");

        } catch (Exception e) {
            System.out.println("❌ Fehler: " + e.getMessage());
        }
    }

    public void handleListBudgets(UUID userId) {
        List<Budget> budgets = service.getBudgetsForUser(userId);

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
