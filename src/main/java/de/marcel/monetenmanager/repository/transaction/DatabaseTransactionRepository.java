package de.marcel.monetenmanager.repository.transaction;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import de.marcel.monetenmanager.domain.shared.Amount;
import de.marcel.monetenmanager.domain.transaction.Transaction;
import de.marcel.monetenmanager.domain.transaction.TransactionRepository;
import de.marcel.monetenmanager.domain.transaction.TransactionType;

@Repository
public class DatabaseTransactionRepository implements TransactionRepository {

    private final TransactionJpaRepository jpaRepository;

    public DatabaseTransactionRepository(TransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity(
            transaction.getId(),
            transaction.getUserId(),
            transaction.getCategory(),
            transaction.getAmount().getValue(),
            TransactionTypeEntity.valueOf(transaction.getType().name()),
            transaction.getTimestamp()
        );

        jpaRepository.save(entity);
    }

    @Override
    public List<Transaction> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId)
                .stream()
                .map(e -> new Transaction(
                        e.getId(),
                        e.getUserId(),
                        e.getCategory(),
                        new Amount(e.getAmount()),
                        TransactionType.valueOf(e.getType().name()),
                        e.getTimestamp()
                ))
                .collect(Collectors.toList());
    }

}
