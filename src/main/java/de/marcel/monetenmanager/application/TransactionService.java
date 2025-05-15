package de.marcel.monetenmanager.application;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import de.marcel.monetenmanager.domain.Transaction;
import de.marcel.monetenmanager.domain.TransactionRepository;
import de.marcel.monetenmanager.domain.TransactionType;

public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void addTransaction(UUID userId, String category, BigDecimal amount, TransactionType type) {
        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                userId,
                category,
                amount,
                type,
                LocalDateTime.now()
        );
        repository.save(transaction);
    }

    public List<Transaction> getTransactionsForUser(UUID userId) {
        return repository.findByUserId(userId);
    }
}
