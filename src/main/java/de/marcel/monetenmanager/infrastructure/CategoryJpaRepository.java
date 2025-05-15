package de.marcel.monetenmanager.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {
    List<CategoryEntity> findByUserId(UUID userId);
    Optional<CategoryEntity> findById(UUID id);
}
