package de.marcel.monetenmanager.application.budget;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import de.marcel.monetenmanager.domain.budget.Budget;
import de.marcel.monetenmanager.domain.budget.BudgetRepository;

public class BudgetServiceTest {

    private BudgetRepository budgetRepository;
    private BudgetService budgetService;

    @BeforeEach
    public void setUp() {
        budgetRepository = mock(BudgetRepository.class);
        budgetService = new BudgetService(budgetRepository);
    }

    @Test
    public void testCreateBudget_savesBudget() {
        UUID userId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        String name = "Monatliches Gaming-Budget";
        BigDecimal amount = new BigDecimal("150.00");
        LocalDate start = LocalDate.of(2025, 5, 1);
        LocalDate end = LocalDate.of(2025, 5, 31);

        budgetService.createBudget(userId, categoryId, name, amount, start, end);

        verify(budgetRepository, times(1)).save(any(Budget.class));
    }

    @Test
    public void testGetBudgetsForUser_returnsList() {
        UUID userId = UUID.randomUUID();
        List<Budget> expected = List.of(
            new Budget(UUID.randomUUID(), userId, UUID.randomUUID(), "Games", new BigDecimal("100.00"),
                       LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 31))
        );

        when(budgetRepository.findByUserId(userId)).thenReturn(expected);

        List<Budget> result = budgetService.getBudgetsForUser(userId);

        assertEquals(1, result.size());
        assertEquals("Games", result.get(0).getName());
        assertEquals(new BigDecimal("100.00"), result.get(0).getAmount());

        verify(budgetRepository, times(1)).findByUserId(userId);
    }
}
