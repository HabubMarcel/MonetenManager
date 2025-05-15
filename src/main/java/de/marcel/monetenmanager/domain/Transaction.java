package de.marcel.monetenmanager.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private final UUID id;
    private final UUID userId;
    private final String category;
    private final BigDecimal amount;
    private final TransactionType type;
    private final LocalDateTime timestamp;

    public Transaction(UUID id, UUID userId, String category, BigDecimal amount, TransactionType type, LocalDateTime timestamp) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
