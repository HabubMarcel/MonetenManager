package de.marcel.monetenmanager.repository.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import de.marcel.monetenmanager.domain.category.Category;
import de.marcel.monetenmanager.domain.category.CategoryRepository;

public class InMemoryCategoryRepository implements CategoryRepository {

    private final Map<UUID, List<Category>> categoriesByUser = new HashMap<>();
    private final Map<UUID, Category> byId = new HashMap<>();

    @Override
    public void save(Category category) {
        categoriesByUser
                .computeIfAbsent(category.getUserId(), k -> new ArrayList<>())
                .add(category);
        byId.put(category.getId(), category);
    }

    @Override
    public List<Category> findByUserId(UUID userId) {
        return categoriesByUser.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public Optional<Category> findById(UUID categoryId) {
        return Optional.ofNullable(byId.get(categoryId));
    }
}
