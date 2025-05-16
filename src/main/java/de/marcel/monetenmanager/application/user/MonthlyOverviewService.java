package de.marcel.monetenmanager.application.user;

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
import de.marcel.monetenmanager.domain.transaction.Transaction;
import de.marcel.monetenmanager.domain.transaction.TransactionRepository;
import de.marcel.monetenmanager.domain.transaction.TransactionType;

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

        // Filter Transaktionen auf Monat + Kategorie-Ausgaben
        Map<String, BigDecimal> sumByCategory = transactions.stream()
        .filter(t -> t.getType() == TransactionType.AUSGABE)
        .filter(t -> YearMonth.from(t.getTimestamp()).equals(month))
        .collect(Collectors.groupingBy(
        Transaction::getCategory, // ✅ das ist ein String
        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
    ));

        List<OverviewEntry> entries = new ArrayList<>();

        for (Category category : categories) {
            BigDecimal total = sumByCategory.getOrDefault(category.getName(), BigDecimal.ZERO);
            // Finde Budget zur Kategorie und Zeitraum
            Optional<Budget> matchingBudget = budgets.stream()
                    .filter(b -> b.getCategoryId().equals(category.getId()))
                    .filter(b -> !month.atEndOfMonth().isBefore(b.getStartDate()))
                    .filter(b -> !month.atDay(1).isAfter(b.getEndDate()))
                    .findFirst();

            entries.add(new OverviewEntry(
                    category.getName(),
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
            BigDecimal budgetAmount,
            boolean hasBudget
    ) {}
}
