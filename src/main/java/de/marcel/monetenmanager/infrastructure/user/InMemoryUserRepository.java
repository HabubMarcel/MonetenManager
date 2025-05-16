package de.marcel.monetenmanager.infrastructure.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import de.marcel.monetenmanager.domain.user.Email;
import de.marcel.monetenmanager.domain.user.User;
import de.marcel.monetenmanager.domain.user.UserRepository;

public class InMemoryUserRepository implements UserRepository {
    private final Map<UUID, User> usersById = new HashMap<>();
    private final Map<Email, User> usersByEmail = new HashMap<>();

    @Override
    public void save(User user) {
        usersById.put(user.getId(), user);
        usersByEmail.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return Optional.ofNullable(usersByEmail.get(email));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(usersById.get(id));
    }
}
