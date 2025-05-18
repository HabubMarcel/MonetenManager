package de.marcel.monetenmanager.domain.user;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import de.marcel.monetenmanager.domain.budget.Budget;
import de.marcel.monetenmanager.domain.budget.BudgetRepository;
import de.marcel.monetenmanager.domain.category.Category;
import de.marcel.monetenmanager.domain.category.CategoryRepository;
import de.marcel.monetenmanager.domain.shared.Amount;
import de.marcel.monetenmanager.domain.transaction.Transaction;
import de.marcel.monetenmanager.domain.transaction.TransactionRepository;
import de.marcel.monetenmanager.domain.transaction.TransactionType;

/**
 * Domain Service für die Monatsübersicht.
 */
public class MonthlyOverviewService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;

    public MonthlyOverviewService(TransactionRepository transactionRepository,
                                   CategoryRepository categoryRepository,
                                   BudgetRepository budgetRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.budgetRepository = budgetRepository;
    }

    public List<OverviewEntry> getMonthlyOverview(UUID userId, YearMonth month) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        List<Category> categories = categoryRepository.findByUserId(userId);
        List<Budget> budgets = budgetRepository.findByUserId(userId);

        // Filtere Transaktionen des Monats
        Map<String, BigDecimal> sumByCategory = transactions.stream()
            .filter(t -> t.getType() == TransactionType.AUSGABE)
            .filter(t -> YearMonth.from(t.getTimestamp()).equals(month))
            .collect(Collectors.groupingBy(
                t -> t.getCategory(),
                Collectors.reducing(BigDecimal.ZERO, t -> t.getAmount().getValue(), BigDecimal::add)
            ));

        List<OverviewEntry> entries = new ArrayList<>();

        for (Category category : categories) {
            BigDecimal total = sumByCategory.getOrDefault(category.getName().toString(), BigDecimal.ZERO);
            Optional<Budget> matchingBudget = budgets.stream()
                .filter(b -> b.getCategoryId().equals(category.getId()))
                .filter(b -> !month.atEndOfMonth().isBefore(b.getStartDate()))
                .filter(b -> !month.atDay(1).isAfter(b.getEndDate()))
                .findFirst();

            entries.add(new OverviewEntry(
                    category.getName().toString(),
                    total,
                    matchingBudget.map(Budget::getAmount).orElse(null),
                    matchingBudget.isPresent()
            ));
        }

        return entries;
    }

    public record OverviewEntry(
        String categoryName,
        BigDecimal spent,
        Amount budgetAmount,
        boolean hasBudget
    ) {}
}
