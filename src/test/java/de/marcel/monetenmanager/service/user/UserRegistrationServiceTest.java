package de.marcel.monetenmanager.service.user;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.marcel.monetenmanager.domain.user.Email;
import de.marcel.monetenmanager.domain.user.User;
import de.marcel.monetenmanager.domain.user.UserRepository;

public class UserRegistrationServiceTest {

    private UserRepository userRepository;
    private UserRegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        registrationService = new UserRegistrationService(userRepository);
    }

    @Test
    public void testRegisterUser_savesUser() {
        String name = "Marcel";
        String emailStr = "marcel@test.de";
        String password = "sicher123";

        registrationService.registerUser(name, emailStr, password);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegisterUser_emailAlreadyExists_throwsException() {
        String name = "Marcel";
        String emailStr = "marcel@test.de";
        String password = "sicher123";

        Email email = new Email(emailStr);
        User existingUser = new User(UUID.randomUUID(), name, email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationService.registerUser(name, emailStr, password);
        });

        assertEquals("E-Mail bereits registriert.", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}
