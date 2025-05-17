package de.marcel.monetenmanager.cli.budget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marcel.monetenmanager.application.budget.BudgetService;
import de.marcel.monetenmanager.application.category.CategoryService;
import de.marcel.monetenmanager.domain.budget.Budget;
import de.marcel.monetenmanager.domain.category.Category;
import de.marcel.monetenmanager.domain.category.CategoryName;
import de.marcel.monetenmanager.domain.shared.Amount;

public class BudgetCLIHandler {

    private static final Logger log = LoggerFactory.getLogger(BudgetCLIHandler.class);

    private final BudgetService service;
    private final CategoryService categoryService;
    private final Scanner scanner;

    public BudgetCLIHandler(BudgetService service, CategoryService categoryService, Scanner scanner) {
        this.service = service;
        this.categoryService = categoryService;
        this.scanner = scanner;
    }

    public void handleCreateBudget(UUID userId) {
        try {
            List<Category> categories = categoryService.getCategoriesForUser(userId);

            if (categories.isEmpty()) {
                System.out.println("❌ Keine Kategorien vorhanden. Bitte zuerst eine Kategorie erstellen.");
                return;
            }

            System.out.println("📂 Verfügbare Kategorien:");
            for (Category c : categories) {
                System.out.printf("→ %s (%s)%s\n",
                        c.getName(),
                        c.getType(),
                        c.isSavings() ? " [💰 Sparziel]" : ""
                );
            }
            System.out.println("⚠️ Nutze eindeutige Namen, um Mehrdeutigkeiten zu vermeiden.");

            System.out.print("Name des Budgets: ");
            String name = scanner.nextLine();

            System.out.print("Kategorie-Name: ");
            String categoryName = scanner.nextLine();

            CategoryName inputName = new CategoryName(categoryName);
            List<Category> matching = categories.stream()
                    .filter(c -> c.getName().equals(inputName))
                    .toList();

            if (matching.isEmpty()) {
                System.out.println("❌ Keine passende Kategorie gefunden.");
                return;
            }
            if (matching.size() > 1) {
                System.out.println("⚠️ Mehrere Kategorien mit diesem Namen gefunden. Bitte ID verwenden.");
                matching.forEach(c ->
                        System.out.printf("→ [%s] %s (%s, %s)%s\n",
                                c.getId(), c.getName(), c.getType(), c.getColor(),
                                c.isSavings() ? " [💰 Sparziel]" : "")
                );
                return;
            }

            UUID categoryId = matching.get(0).getId();

            System.out.print("Betrag (z. B. 100.00): ");
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            LocalDate startDate = readValidDate("Startdatum (YYYY-MM-DD): ");
            LocalDate endDate = readValidDate("Enddatum (YYYY-MM-DD): ");

            service.createBudget(userId, categoryId, name, new Amount(amount), startDate, endDate);
            System.out.println("✅ Budget gespeichert.");
            log.info("Budget für Benutzer {} erstellt: {}", userId, name);

        } catch (Exception e) {
            System.out.println("❌ Fehler: " + e.getMessage());
            log.error("Fehler beim Erstellen eines Budgets", e);
        }
    }

    private LocalDate readValidDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Ungültiges Datum. Bitte im Format YYYY-MM-DD eingeben.");
            }
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
                        b.getId(),
                        b.getName(),
                        b.getAmount().getValue(), // 🔧 Zugriff auf BigDecimal aus Amount
                        b.getStartDate(),
                        b.getEndDate(),
                        b.getCategoryId()
                );
            }
        }
    }
}
