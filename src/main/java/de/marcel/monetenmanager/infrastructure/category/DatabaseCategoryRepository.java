package de.marcel.monetenmanager.infrastructure.category;

import de.marcel.monetenmanager.domain.category.Category;
import de.marcel.monetenmanager.domain.category.CategoryRepository;
import de.marcel.monetenmanager.domain.category.CategoryType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class DatabaseCategoryRepository implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;

    public DatabaseCategoryRepository(CategoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Category category) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(category.getId());
        entity.setUserId(category.getUserId());
        entity.setName(category.getName());
        entity.setType(Enum.valueOf(CategoryTypeEntity.class, category.getType().name()));
        entity.setColor(category.getColor());
        entity.setSavings(category.isSavings()); // ✅ NEU
        jpaRepository.save(entity);
    }

    @Override
    public List<Category> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(e -> new Category(
                        e.getId(),
                        e.getUserId(),
                        e.getName(),
                        CategoryType.valueOf(e.getType().name()),
                        e.getColor(),
                        e.isSavings() // ✅ NEU
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findById(UUID categoryId) {
        return jpaRepository.findById(categoryId)
                .map(e -> new Category(
                        e.getId(),
                        e.getUserId(),
                        e.getName(),
                        CategoryType.valueOf(e.getType().name()),
                        e.getColor(),
                        e.isSavings() // ✅ NEU
                ));
    }
}
