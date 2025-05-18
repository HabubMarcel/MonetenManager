package de.marcel.monetenmanager.repository.budget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.marcel.monetenmanager.domain.budget.Budget;
import de.marcel.monetenmanager.domain.budget.BudgetRepository;

public class InMemoryBudgetRepository implements BudgetRepository {

    private final Map<UUID, List<Budget>> budgetsByUser = new HashMap<>();

    @Override
    public void save(Budget budget) {
        budgetsByUser
                .computeIfAbsent(budget.getUserId(), k -> new ArrayList<>())
                .add(budget);
    }

    @Override
    public List<Budget> findByUserId(UUID userId) {
        return budgetsByUser.getOrDefault(userId, Collections.emptyList());
    }
}
