package de.marcel.monetenmanager.application.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.marcel.monetenmanager.domain.transaction.Transaction;
import de.marcel.monetenmanager.domain.transaction.TransactionRepository;
import de.marcel.monetenmanager.domain.transaction.TransactionType;

public class TransactionServiceTest {

    private TransactionRepository transactionRepository;
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    public void testCreateTransaction_savesTransaction() {
        UUID userId = UUID.randomUUID();
        String category = "Essen";
        BigDecimal amount = new BigDecimal("50.00");
        TransactionType type = TransactionType.AUSGABE;

        transactionService.createTransaction(userId, category, amount, type);

        // Prüfen, ob repository.save(...) einmal aufgerufen wurde
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testGetTransactionsForUser_returnsList() {
        UUID userId = UUID.randomUUID();
        List<Transaction> expected = List.of(
            new Transaction(
                UUID.randomUUID(),
                userId,
                "Essen",
                new BigDecimal("20.00"),
                TransactionType.AUSGABE,
                LocalDateTime.now()
            )
        );

        when(transactionRepository.findByUserId(userId)).thenReturn(expected);

        List<Transaction> result = transactionService.getTransactionsForUser(userId);

        assertEquals(1, result.size());
        assertEquals(expected.get(0).getUserId(), result.get(0).getUserId());
        assertEquals(expected.get(0).getCategory(), result.get(0).getCategory());

        verify(transactionRepository, times(1)).findByUserId(userId);
    }
}
