package de.marcel.monetenmanager.domain.transaction;

import java.time.LocalDateTime;
import java.util.UUID;

import de.marcel.monetenmanager.domain.shared.Amount;

public class Transaction {

    private final UUID id;
    private final UUID userId;
    private final String category;
    private final Amount amount;
    private final TransactionType type;
    private final LocalDateTime timestamp;

    public Transaction(UUID id, UUID userId, String category, Amount amount, TransactionType type, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getCategory() {
        return category;
    }

    public Amount getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
