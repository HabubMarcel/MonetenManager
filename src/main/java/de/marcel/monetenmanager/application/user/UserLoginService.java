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

    public Optional<User> login(String emailRaw, String password) {
        Email email = new Email(emailRaw);
        return userRepository.findByEmail(email)
        .filter(user -> user.getPassword().equals(password));
    }
}
