package de.marcel.monetenmanager.service.budget;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import de.marcel.monetenmanager.domain.budget.Budget;
import de.marcel.monetenmanager.domain.budget.BudgetRepository;
import de.marcel.monetenmanager.domain.shared.Amount;

public class BudgetService {

    private final BudgetRepository repository;

    public BudgetService(BudgetRepository repository) {
        this.repository = repository;
    }

    public void createBudget(UUID userId, UUID categoryId, String name, Amount amount, LocalDate startDate, LocalDate endDate) {
        Budget budget = new Budget(UUID.randomUUID(), userId, categoryId, name, amount, startDate, endDate);
        repository.save(budget);
    }

    public List<Budget> getBudgetsForUser(UUID userId) {
        return repository.findByUserId(userId);
    }
}
