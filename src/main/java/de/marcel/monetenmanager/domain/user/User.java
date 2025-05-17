package de.marcel.monetenmanager.domain.user;

import de.marcel.monetenmanager.domain.shared.AggregateRoot;

import java.util.UUID;

public class User implements AggregateRoot {
    private final UUID id;
    private final String name;
    private final Email email;
    private final String password;

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
