package de.marcel.monetenmanager.domain.shared;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        if (start == null || end == null || end.isBefore(start)) {
            throw new IllegalArgumentException("Ungültiger Zeitraum");
        }
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public boolean contains(LocalDate date) {
        return (date.equals(start) || date.isAfter(start)) && (date.equals(end) || date.isBefore(end));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Period that &&
               Objects.equals(start, that.start) &&
               Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return start + " bis " + end;
    }
}
