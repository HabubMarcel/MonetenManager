package de.marcel.monetenmanager.service.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.marcel.monetenmanager.domain.shared.Amount;
import de.marcel.monetenmanager.domain.transaction.Transaction;
import de.marcel.monetenmanager.domain.transaction.TransactionRepository;
import de.marcel.monetenmanager.domain.transaction.TransactionType;

public class TransactionServiceTest {

    private TransactionRepository repository;
    private TransactionService service;

    @BeforeEach
    public void setUp() {
        repository = mock(TransactionRepository.class);
        service = new TransactionService(repository);
    }

    @Test
    public void testCreateTransaction_savesTransaction() {
        UUID userId = UUID.randomUUID();
        String category = "Essen";
        Amount amount = new Amount(new BigDecimal("100.00"));
        TransactionType type = TransactionType.AUSGABE;

        service.createTransaction(userId, category, amount, type);

        verify(repository).save(argThat(t ->
                t.getUserId().equals(userId) &&
                t.getCategory().equals(category) &&
                t.getAmount().getValue().compareTo(new BigDecimal("100.00")) == 0 &&
                t.getType() == type
        ));
    }

    @Test
    public void testGetTransactionsForUser_returnsList() {
        UUID userId = UUID.randomUUID();
        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                userId,
                "Essen",
                new Amount(new BigDecimal("20.00")),
                TransactionType.AUSGABE,
                LocalDateTime.now()
        );

        when(repository.findByUserId(userId)).thenReturn(List.of(transaction));

        List<Transaction> result = service.getTransactionsForUser(userId);

        assertEquals(1, result.size());
        assertEquals("Essen", result.get(0).getCategory());
        verify(repository, times(1)).findByUserId(userId);
    }
}
