package de.marcel.monetenmanager.application.user;

import java.util.Optional;
import java.util.UUID;

import de.marcel.monetenmanager.domain.user.Email;
import de.marcel.monetenmanager.domain.user.User;
import de.marcel.monetenmanager.domain.user.UserRepository;

public class UserRegistrationService {
    private final UserRepository userRepository;

    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID registerUser(String name, String emailRaw, String password) {
        Email email = new Email(emailRaw);

        Optional<User> existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("E-Mail bereits registriert.");
        }

        UUID userId = UUID.randomUUID();
        User user = new User(userId, name, email, password);
        userRepository.save(user);
        return userId;
    }
}
