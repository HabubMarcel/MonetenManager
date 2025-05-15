package de.marcel.monetenmanager.domain;

import java.util.List;
import java.util.UUID;

public interface BudgetRepository {
    void save(Budget budget);
    List<Budget> findByUserId(UUID userId);
}
