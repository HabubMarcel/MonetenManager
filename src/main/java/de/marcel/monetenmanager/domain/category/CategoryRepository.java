package de.marcel.monetenmanager.domain.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    void save(Category category);
    List<Category> findByUserId(UUID userId);
    Optional<Category> findById(UUID categoryId);
}
