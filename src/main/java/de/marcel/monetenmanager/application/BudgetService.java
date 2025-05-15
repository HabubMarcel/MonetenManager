package de.marcel.monetenmanager.application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import de.marcel.monetenmanager.domain.Budget;
import de.marcel.monetenmanager.domain.BudgetRepository;

public class BudgetService {

    private final BudgetRepository repository;

    public BudgetService(BudgetRepository repository) {
        this.repository = repository;
    }

    public void createBudget(UUID userId, UUID categoryId, String name, BigDecimal amount, LocalDate startDate, LocalDate endDate) {
        Budget budget = new Budget(UUID.randomUUID(), userId, categoryId, name, amount, startDate, endDate);
        repository.save(budget);
    }

    public List<Budget> getBudgetsForUser(UUID userId) {
        return repository.findByUserId(userId);
    }
}
