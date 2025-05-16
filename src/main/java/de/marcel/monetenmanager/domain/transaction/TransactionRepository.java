package de.marcel.monetenmanager.domain.transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {
    void save(Transaction transaction);
    List<Transaction> findByUserId(UUID userId);
}
