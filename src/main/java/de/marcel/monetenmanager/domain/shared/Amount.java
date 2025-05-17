package de.marcel.monetenmanager.domain.shared;

import java.math.BigDecimal;
import java.util.Objects;

public class Amount {

    private final BigDecimal value;

    public Amount(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must not be null or negative.");
        }
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Amount add(Amount other) {
        return new Amount(this.value.add(other.value));
    }

    public Amount subtract(Amount other) {
        return new Amount(this.value.subtract(other.value));
    }

    public boolean isGreaterThan(Amount other) {
        return this.value.compareTo(other.value) > 0;
    }

    public boolean isLessThan(Amount other) {
        return this.value.compareTo(other.value) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Amount amount)) return false;
        return value.compareTo(amount.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.stripTrailingZeros());
    }

    @Override
    public String toString() {
        return value + " €";
    }
}
