package de.marcel.monetenmanager.infrastructure.budget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "budgets")
public class BudgetEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID categoryId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    public BudgetEntity() {}

    public BudgetEntity(UUID id, UUID userId, UUID categoryId, String name, BigDecimal amount, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.name = name;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getCategoryId() { return categoryId; }
    public String getName() { return name; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
}
