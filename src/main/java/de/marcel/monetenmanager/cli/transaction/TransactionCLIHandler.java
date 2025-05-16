package de.marcel.monetenmanager.cli.transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.marcel.monetenmanager.application.transaction.TransactionService;
import de.marcel.monetenmanager.domain.transaction.Transaction;
import de.marcel.monetenmanager.domain.transaction.TransactionType;

public class TransactionCLIHandler {

    private final TransactionService transactionService;
    private final Scanner scanner;
    private static final Logger log = LoggerFactory.getLogger(TransactionCLIHandler.class);

    public TransactionCLIHandler(TransactionService transactionService, Scanner scanner) {
        this.transactionService = transactionService;
        this.scanner = scanner;
    }

    public void handleAddTransaction(UUID userId) {
        try {
            System.out.print("Kategorie: ");
            String category = scanner.nextLine();

            System.out.print("Betrag (z. B. 25.50): ");
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            System.out.print("Typ (EINNAHME oder AUSGABE): ");
            TransactionType type = TransactionType.valueOf(scanner.nextLine().toUpperCase());

            transactionService.createTransaction(userId, category, amount, type);
            log.info("Transaktion gespeichert: Kategorie={}, Betrag={}, Typ={}", category, amount, type);
            System.out.println("✅ Transaktion gespeichert.");

        } catch (Exception e) {
            log.error("❌ Fehler beim Hinzufügen der Transaktion", e);
            System.out.println("❌ Transaktion konnte nicht gespeichert werden.");
        }
    }

    public void handleListTransactions(UUID userId) {
        List<Transaction> transactions = transactionService.getTransactionsForUser(userId);

        if (transactions.isEmpty()) {
            System.out.println("ℹ️ Keine Transaktionen gefunden.");
        } else {
            System.out.println("📄 Transaktionen:");
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
