package de.marcel.monetenmanager.infrastructure.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import de.marcel.monetenmanager.domain.category.*;

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
        entity.setName(category.getName().getValue());
        entity.setType(CategoryTypeEntity.valueOf(category.getType().name()));
        entity.setColor(category.getColor().getValue());
        entity.setSavings(category.isSavings());
        jpaRepository.save(entity);
    }

    @Override
    public List<Category> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(e -> new Category(
                        e.getId(),
                        e.getUserId(),
                        new CategoryName(e.getName()),
                        CategoryType.valueOf(e.getType().name()),
                        new CategoryColor(e.getColor()),
                        e.isSavings()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findById(UUID categoryId) {
        return jpaRepository.findById(categoryId)
                .map(e -> new Category(
                        e.getId(),
                        e.getUserId(),
                        new CategoryName(e.getName()),
                        CategoryType.valueOf(e.getType().name()),
                        new CategoryColor(e.getColor()),
                        e.isSavings()
                ));
    }
}
