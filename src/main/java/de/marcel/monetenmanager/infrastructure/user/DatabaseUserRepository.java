package de.marcel.monetenmanager.infrastructure.user;

import java.util.Optional;
import java.util.UUID;

import de.marcel.monetenmanager.domain.user.Email;
import de.marcel.monetenmanager.domain.user.User;
import de.marcel.monetenmanager.domain.user.UserRepository;

public class DatabaseUserRepository implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public DatabaseUserRepository(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(User user) {
        UserEntity entity = new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail().getValue(),
                user.getPassword()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.getValue())
            .map(this::mapToDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(this::mapToDomain);
    }

    private User mapToDomain(UserEntity entity) {
    return new User(
            entity.getId(),
            entity.getName(),
            new Email(entity.getEmail()),
            entity.getPassword()
        );
    }
}
