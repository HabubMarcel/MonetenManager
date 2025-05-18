package de.marcel.monetenmanager.repository.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.marcel.monetenmanager.domain.transaction.Transaction;
import de.marcel.monetenmanager.domain.transaction.TransactionRepository;

public class InMemoryTransactionRepository implements TransactionRepository {

    private final Map<UUID, List<Transaction>> transactionsByUser = new HashMap<>();

    @Override
    public void save(Transaction transaction) {
        transactionsByUser
                .computeIfAbsent(transaction.getUserId(), k -> new ArrayList<>())
                .add(transaction);
    }

    @Override
    public List<Transaction> findByUserId(UUID userId) {
        return transactionsByUser.getOrDefault(userId, Collections.emptyList());
    }
}
