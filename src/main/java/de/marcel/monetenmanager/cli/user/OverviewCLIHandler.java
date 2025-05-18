package de.marcel.monetenmanager.cli.user;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marcel.monetenmanager.domain.user.MonthlyOverviewService;
import de.marcel.monetenmanager.domain.user.MonthlyOverviewService.OverviewEntry;

public class OverviewCLIHandler {

    private static final Logger log = LoggerFactory.getLogger(OverviewCLIHandler.class);

    private final MonthlyOverviewService service;
    private final Scanner scanner;

    public OverviewCLIHandler(MonthlyOverviewService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void handleOverview(UUID userId) {
        try {
            YearMonth month = readValidMonth("Für welchen Monat (YYYY-MM)? ");
            List<OverviewEntry> entries = service.getMonthlyOverview(userId, month);

            if (entries.isEmpty()) {
                System.out.println("Keine Ausgaben in diesem Monat.");
                return;
            }

            System.out.printf("Übersicht für %s\n", month);
            for (OverviewEntry entry : entries) {
                String status = "";
                if (entry.hasBudget()) {
                    if (entry.spent().compareTo(entry.budgetAmount().getValue()) > 0) {
                        status = "!Überschritten!";
                    } else {
                        status = "Im Rahmen";
                    }
                }
                String icon = entry.hasBudget() && entry.budgetAmount() != null && entry.budgetAmount().getValue().signum() == 0
                        ? "€" : "";
                System.out.printf("- %s%s: %.2f €", entry.categoryName(), icon, entry.spent());
                if (entry.budgetAmount() != null) {
                    System.out.printf(" / %.2f € Budget (%s)", entry.budgetAmount().getValue(), status);
                }
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("X Fehler bei der Übersicht.");
            log.error("Fehler beim Abrufen der Monatsübersicht", e);
        }
    }

    private YearMonth readValidMonth(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return YearMonth.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("X Ungültiges Format. Bitte im Format YYYY-MM eingeben.");
            }
        }
    }
}
