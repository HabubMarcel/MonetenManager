package de.marcel.monetenmanager.application.transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import de.marcel.monetenmanager.domain.shared.Amount;
import de.marcel.monetenmanager.domain.transaction.Transaction;
import de.marcel.monetenmanager.domain.transaction.TransactionRepository;
import de.marcel.monetenmanager.domain.transaction.TransactionType;

@Component
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void createTransaction(UUID userId, String category, Amount amount, TransactionType type) {
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
