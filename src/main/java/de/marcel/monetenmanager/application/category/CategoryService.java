package de.marcel.monetenmanager.application.category;

import java.util.List;
import java.util.UUID;

import de.marcel.monetenmanager.domain.category.*;

public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public void createCategory(UUID userId, CategoryName name, CategoryType type, CategoryColor color, boolean isSavings) {
    Category category = new Category(UUID.randomUUID(), userId, name, type, color, isSavings);
    repository.save(category);
    }

    public List<Category> getCategoriesForUser(UUID userId) {
        return repository.findByUserId(userId);
    }

    public Category getById(UUID categoryId) {
        return repository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Kategorie nicht gefunden"));
    }
}
