package de.marcel.monetenmanager.domain.budget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import de.marcel.monetenmanager.domain.shared.Amount;

public class Budget {

    private final Amount amount;
    private final UUID id;
    private final UUID userId;
    private final UUID categoryId;
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Budget(UUID id, UUID userId, UUID categoryId, String name, Amount amount, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.name = name;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public String getName(){
        return name;
    }
    
    public Amount getAmount() {
        return amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
