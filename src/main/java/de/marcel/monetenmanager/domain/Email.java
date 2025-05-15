package de.marcel.monetenmanager.domain;

import java.util.regex.Pattern;

public class Email {
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    private final String value;

    public Email(String value) {
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Ungültige E-Mail-Adresse");
        }
        this.value = value.toLowerCase();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    // equals & hashCode wichtig für Vergleiche
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Email)) return false;
        return value.equals(((Email) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
