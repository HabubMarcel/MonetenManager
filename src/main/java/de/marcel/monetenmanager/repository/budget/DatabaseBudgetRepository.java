package de.marcel.monetenmanager.repository.budget;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import de.marcel.monetenmanager.domain.budget.Budget;
import de.marcel.monetenmanager.domain.budget.BudgetRepository;
import de.marcel.monetenmanager.domain.shared.Amount;

public class DatabaseBudgetRepository implements BudgetRepository {

    private final BudgetJpaRepository jpaRepository;

    public DatabaseBudgetRepository(BudgetJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Budget budget) {
        BudgetEntity entity = new BudgetEntity(
                budget.getId(),
                budget.getUserId(),
                budget.getCategoryId(),
                budget.getName(),
                budget.getAmount().getValue(),
                budget.getStartDate(),
                budget.getEndDate()
        );
        jpaRepository.save(entity);
    }

    @Override
    public List<Budget> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(e -> new Budget(
                        e.getId(),
                        e.getUserId(),
                        e.getCategoryId(),
                        e.getName(),
                        new Amount(e.getAmount()),
                        e.getStartDate(),
                        e.getEndDate()
                ))
                .collect(Collectors.toList());
    }
}
