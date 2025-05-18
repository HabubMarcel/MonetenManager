package de.marcel.monetenmanager.domain.category;

import java.util.Objects;

public class CategoryColor {
    private final String value;

    public CategoryColor(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Farbe darf nicht leer sein.");
        }
        this.value = value.trim().toLowerCase();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof CategoryColor other && value.equals(other.value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
