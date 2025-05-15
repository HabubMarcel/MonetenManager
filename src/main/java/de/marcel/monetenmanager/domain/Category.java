package de.marcel.monetenmanager.domain;

import java.util.UUID;

public class Category {

    private final UUID id;
    private final UUID userId;
    private final String name;
    private final CategoryType type;
    private final String color;

    public Category(UUID id, UUID userId, String name, CategoryType type, String color) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public CategoryType getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

}
