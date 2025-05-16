package de.marcel.monetenmanager.application;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.marcel.monetenmanager.domain.Email;
import de.marcel.monetenmanager.domain.User;
import de.marcel.monetenmanager.domain.UserRepository;

public class UserLoginServiceTest {

    private UserRepository userRepository;
    private UserLoginService loginService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        loginService = new UserLoginService(userRepository);
    }

    @Test
    public void testLogin_successful() {
        UUID id = UUID.randomUUID();
        String name = "Marcel";
        Email email = new Email("test@mail.com");
        String password = "geheim";

        User user = new User(id, name, email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = loginService.login("test@mail.com", password);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    public void testLogin_wrongPassword() {
        Email email = new Email("test@mail.com");
        User user = new User(UUID.randomUUID(), "Marcel", email, "richtig");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = loginService.login("test@mail.com", "falsch");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testLogin_userNotFound() {
        Email email = new Email("nichtda@mail.com");

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = loginService.login("nichtda@mail.com", "pass");

        assertTrue(result.isEmpty());
    }
}
