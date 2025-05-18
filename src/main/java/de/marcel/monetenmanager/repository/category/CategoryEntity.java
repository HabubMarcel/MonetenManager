package de.marcel.monetenmanager.repository.category;

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

    @Column(nullable = false)
    private boolean isSavings;

    // Standard-Konstruktor
    public CategoryEntity() {}

    // Getter
    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getName() { return name; }
    public CategoryTypeEntity getType() { return type; }
    public String getColor() { return color; }
    public boolean isSavings() { return isSavings; }

    // Setter
    public void setId(UUID id) { this.id = id; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setType(CategoryTypeEntity type) { this.type = type; }
    public void setColor(String color) { this.color = color; }
    public void setSavings(boolean savings) { this.isSavings = savings; }
}