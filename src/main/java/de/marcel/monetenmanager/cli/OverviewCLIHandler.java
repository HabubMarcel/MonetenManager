package de.marcel.monetenmanager.cli;

import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import de.marcel.monetenmanager.application.MonthlyOverviewService;

public class OverviewCLIHandler {

    private final MonthlyOverviewService service;
    private final Scanner scanner;

    public OverviewCLIHandler(MonthlyOverviewService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void handleOverview(UUID userId) {
        try {
            System.out.print("Für welchen Monat? (z. B. 2025-05): ");
            String input = scanner.nextLine();
            YearMonth month = YearMonth.parse(input);

            List<MonthlyOverviewService.OverviewEntry> entries = service.getMonthlyOverview(userId, month);

            System.out.println("\n📊 Übersicht für " + month.getMonth() + " " + month.getYear());
            System.out.println("-----------------------------------------");

            for (var entry : entries) {
                String status;
                if (!entry.hasBudget()) {
                    status = "(kein Budget)";
                } else if (entry.spent().compareTo(entry.budgetAmount()) <= 0) {
                    status = "✅";
                } else {
                    status = "❌ Budget überschritten";
                }

                System.out.printf("• %s: %.2f € von %s %s\n",
                        entry.categoryName(),
                        entry.spent(),
                        entry.budgetAmount() != null ? entry.budgetAmount() + " €" : "-",
                        status
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Fehler beim Anzeigen der Übersicht: " + e.getMessage());
        }
    }
}
