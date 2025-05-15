package de.marcel.monetenmanager.infrastructure;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionTypeEntity type;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public TransactionEntity() {}

    public TransactionEntity(UUID id, UUID userId, String category, BigDecimal amount, TransactionTypeEntity type, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    // Getter
    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getCategory() { return category; }
    public BigDecimal getAmount() { return amount; }
    public TransactionTypeEntity getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
