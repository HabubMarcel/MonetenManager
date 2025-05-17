package de.marcel.monetenmanager.cli;

import de.marcel.monetenmanager.cli.budget.BudgetCLIHandler;
import de.marcel.monetenmanager.cli.category.CategoryCLIHandler;
import de.marcel.monetenmanager.cli.transaction.TransactionCLIHandler;
import de.marcel.monetenmanager.cli.user.OverviewCLIHandler;
import de.marcel.monetenmanager.cli.user.UserCLIHandler;

import java.util.Scanner;

public class CLIHandlerFactory {

    private final UserCLIHandler userHandler;
    private final TransactionCLIHandler transactionHandler;
    private final CategoryCLIHandler categoryHandler;
    private final BudgetCLIHandler budgetHandler;
    private final OverviewCLIHandler overviewHandler;

    public CLIHandlerFactory(
            UserCLIHandler userHandler,
            TransactionCLIHandler transactionHandler,
            CategoryCLIHandler categoryHandler,
            BudgetCLIHandler budgetHandler,
            OverviewCLIHandler overviewHandler
    ) {
        this.userHandler = userHandler;
        this.transactionHandler = transactionHandler;
        this.categoryHandler = categoryHandler;
        this.budgetHandler = budgetHandler;
        this.overviewHandler = overviewHandler;
    }

    public UserCLIHandler userHandler() {
        return userHandler;
    }

    public TransactionCLIHandler transactionHandler() {
        return transactionHandler;
    }

    public CategoryCLIHandler categoryHandler() {
        return categoryHandler;
    }

    public BudgetCLIHandler budgetHandler() {
        return budgetHandler;
    }

    public OverviewCLIHandler overviewHandler() {
        return overviewHandler;
    }
}

