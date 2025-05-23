package de.marcel.monetenmanager.repository.budget;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetJpaRepository extends JpaRepository<BudgetEntity, UUID> {
    List<BudgetEntity> findByUserId(UUID userId);
}
