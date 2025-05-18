package de.marcel.monetenmanager.domain.category;

import java.util.Objects;
import java.util.UUID;

public class Category {

    private final UUID id;
    private final UUID userId;
    private final CategoryName name;
    private final CategoryType type;
    private final CategoryColor color;
    private final boolean isSavings;

    public Category(UUID id, UUID userId, CategoryName name, CategoryType type, CategoryColor color, boolean isSavings) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.color = color;
        this.isSavings = isSavings;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public CategoryName getName() {
        return name;
    }

    public CategoryType getType() {
        return type;
    }

    public CategoryColor getColor() {
        return color;
    }

    public boolean isSavings() {
        return isSavings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
