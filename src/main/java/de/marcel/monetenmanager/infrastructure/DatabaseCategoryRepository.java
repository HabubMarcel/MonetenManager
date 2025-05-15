package de.marcel.monetenmanager.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import de.marcel.monetenmanager.domain.Category;
import de.marcel.monetenmanager.domain.CategoryRepository;
import de.marcel.monetenmanager.domain.CategoryType;

public class DatabaseCategoryRepository implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;

    public DatabaseCategoryRepository(CategoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Category category) {
        CategoryEntity entity = new CategoryEntity(
                category.getId(),
                category.getUserId(),
                category.getName(),
                CategoryTypeEntity.valueOf(category.getType().name()),
                category.getColor()
        );
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
                        e.getColor()
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
                        e.getColor()
                ));
    }
}
