package de.marcel.monetenmanager.application.user;

import java.util.Optional;

import de.marcel.monetenmanager.domain.user.Email;
import de.marcel.monetenmanager.domain.user.User;
import de.marcel.monetenmanager.domain.user.UserRepository;

public class UserLoginService {
    private final UserRepository userRepository;

    public UserLoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> login(String emailStr, String password) {
        Email email;
        try {
            email = new Email(emailStr);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt;
        }

        return Optional.empty();
    }
}
