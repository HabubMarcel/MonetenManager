package de.marcel.monetenmanager.infrastructure.category;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryTypeEntity type;

    @Column(nullable = false)
    private String color;

    public CategoryEntity() {}

    public CategoryEntity(UUID id, UUID userId, String name, CategoryTypeEntity type, String color) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.color = color;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getName() { return name; }
    public CategoryTypeEntity getType() { return type; }
    public String getColor() { return color; }
}
