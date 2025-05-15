package de.marcel.monetenmanager.application;

import java.util.List;
import java.util.UUID;

import de.marcel.monetenmanager.domain.Category;
import de.marcel.monetenmanager.domain.CategoryRepository;
import de.marcel.monetenmanager.domain.CategoryType;

public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public void createCategory(UUID userId, String name, CategoryType type, String color) {
    List<Category> existing = repository.findByUserId(userId);
    boolean nameExists = existing.stream()
        .anyMatch(c -> c.getName().equalsIgnoreCase(name));

    if (nameExists) {
        throw new IllegalArgumentException("Kategorie mit diesem Namen existiert bereits.");
    }

    Category category = new Category(UUID.randomUUID(), userId, name, type, color);
    repository.save(category);
}
    public List<Category> getCategoriesForUser(UUID userId) {
        return repository.findByUserId(userId);
    }
}
