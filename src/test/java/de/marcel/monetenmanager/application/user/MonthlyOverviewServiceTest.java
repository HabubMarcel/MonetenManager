package de.marcel.monetenmanager.application.user;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.marcel.monetenmanager.domain.budget.BudgetRepository;
import de.marcel.monetenmanager.domain.category.Category;
import de.marcel.monetenmanager.domain.category.CategoryRepository;
import de.marcel.monetenmanager.domain.category.CategoryType;
import de.marcel.monetenmanager.domain.transaction.Transaction;
import de.marcel.monetenmanager.domain.transaction.TransactionRepository;
import de.marcel.monetenmanager.domain.transaction.TransactionType;

public class MonthlyOverviewServiceTest {

    private TransactionRepository transactionRepository;
    private CategoryRepository categoryRepository;
    private BudgetRepository budgetRepository;
    private MonthlyOverviewService overviewService;

    @BeforeEach
    public void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        budgetRepository = mock(BudgetRepository.class);

        overviewService = new MonthlyOverviewService(transactionRepository, categoryRepository, budgetRepository);
    }

    @Test
    public void testGenerateMonthlyOverview_returnsCorrectStructure() {
        UUID userId = UUID.randomUUID();
        LocalDate month = LocalDate.of(2025, 5, 1);

        Transaction t1 = new Transaction(UUID.randomUUID(), userId, "Essen", new BigDecimal("50.00"),
                                         TransactionType.AUSGABE, LocalDateTime.of(2025, 5, 10, 10, 0));
        Transaction t2 = new Transaction(UUID.randomUUID(), userId, "Essen", new BigDecimal("30.00"),
                                         TransactionType.AUSGABE, LocalDateTime.of(2025, 5, 12, 12, 0));

        when(transactionRepository.findByUserId(userId)).thenReturn(List.of(t1, t2));
        when(categoryRepository.findByUserId(userId)).thenReturn(List.of(
            new Category(UUID.randomUUID(), userId, "Essen", CategoryType.AUSGABE, "rot")
        ));

        List<MonthlyOverviewService.OverviewEntry> overview = overviewService.getMonthlyOverview(userId, YearMonth.of(2025, 5));

        assertEquals(1, overview.size());
        assertEquals("Essen", overview.get(0).categoryName());
        assertEquals(new BigDecimal("80.00"), overview.get(0).spent());

        verify(transactionRepository, times(1)).findByUserId(userId);
    }
}
