package de.marcel.monetenmanager.cli.transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marcel.monetenmanager.domain.category.Category;
import de.marcel.monetenmanager.domain.category.CategoryName;
import de.marcel.monetenmanager.domain.shared.Amount;
import de.marcel.monetenmanager.domain.transaction.Transaction;
import de.marcel.monetenmanager.domain.transaction.TransactionType;
import de.marcel.monetenmanager.service.category.CategoryService;
import de.marcel.monetenmanager.service.transaction.TransactionService;


public class TransactionCLIHandler {

    private static final Logger log = LoggerFactory.getLogger(TransactionCLIHandler.class);
    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final Scanner scanner;

    public TransactionCLIHandler(TransactionService transactionService, CategoryService categoryService, Scanner scanner) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
        this.scanner = scanner;
    }
    

    public void handleAddTransaction(UUID userId) {
    try {
        // Kategorien anzeigen
        List<Category> categories = categoryService.getCategoriesForUser(userId);

        if (categories.isEmpty()) {
            System.out.println("! Du hast noch keine Kategorien erstellt. Bitte zuerst eine anlegen.");
            return;
        }

        System.out.println("Verfügbare Kategorien:");
        for (Category c : categories) {
            System.out.printf("→ %s (%s, %s)\n", c.getName(), c.getType(), c.getColor());
        }

        System.out.println("Bitte gib den Namen einer Kategorie ein.");
        System.out.println("Hinweis: Kategorien sollten eindeutige Namen haben!");

        System.out.print("Kategorie-Name: ");
        String categoryName = scanner.nextLine();

        // Kategorie suchen
        CategoryName inputName = new CategoryName(categoryName);

        List<Category> matched = categories.stream()
            .filter(c -> c.getName().equals(inputName))
            .toList();

        if (matched.isEmpty()) {
            System.out.println("X Keine Kategorie mit diesem Namen gefunden.");
            return;
        }
        if (matched.size() > 1) {
            System.out.println("! Mehrere Kategorien mit dem Namen gefunden. Bitte Namen eindeutiger wählen.");
            for (Category c : matched) {
                System.out.printf("→ [%s] %s (%s, %s)\n", c.getId(), c.getName(), c.getType(), c.getColor());
            }
            return;
        }

        String category = matched.get(0).getName().getValue();

        System.out.print("Betrag (z.B. 50.00): ");
        BigDecimal amountInput = new BigDecimal(scanner.nextLine());
        Amount amount = new Amount(amountInput);

        System.out.print("Typ (EINNAHME/AUSGABE): ");
        TransactionType type = TransactionType.valueOf(scanner.nextLine().toUpperCase());

        transactionService.createTransaction(userId, category, amount, type);
        System.out.println("Transaktion gespeichert.");

    } catch (Exception e) {
        log.error("X Fehler beim Hinzufügen der Transaktion", e);
        System.out.println("X Transaktion konnte nicht gespeichert werden.");
    }
}


    public void handleListTransactions(UUID userId) {
        List<Transaction> transactions = transactionService.getTransactionsForUser(userId);

        if (transactions.isEmpty()) {
            System.out.println("Keine Transaktionen gefunden.");
        } else {
            System.out.println("Transaktionen:");
            for (Transaction t : transactions) {
                System.out.printf("[%s] %s %.2f € in %s (%s)\n",
                        t.getTimestamp(),
                        t.getType(),
                        t.getAmount(),
                        t.getCategory(),
                        t.getId()
                );
            }
        }
    }
}
