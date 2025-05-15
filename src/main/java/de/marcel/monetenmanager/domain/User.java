package de.marcel.monetenmanager.domain;

import java.util.UUID;

public class User {
    private final UUID id;
    private final String name;
    private final Email email;
    private final String password; // im echten Leben gehashed!

    public User(UUID id, String name, Email email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
