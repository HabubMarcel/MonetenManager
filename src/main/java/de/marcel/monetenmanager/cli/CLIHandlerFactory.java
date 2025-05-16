package de.marcel.monetenmanager.cli;

import de.marcel.monetenmanager.cli.budget.BudgetCLIHandler;
import de.marcel.monetenmanager.cli.category.CategoryCLIHandler;
import de.marcel.monetenmanager.cli.transaction.TransactionCLIHandler;
import de.marcel.monetenmanager.cli.user.UserCLIHandler;

public class CLIHandlerFactory {

    private final UserCLIHandler userHandler;
    private final CategoryCLIHandler categoryHandler;
    private final TransactionCLIHandler transactionHandler;
    private final BudgetCLIHandler budgetHandler;

    public CLIHandlerFactory(UserCLIHandler userHandler,
                             CategoryCLIHandler categoryHandler,
                             TransactionCLIHandler transactionHandler,
                             BudgetCLIHandler budgetHandler) {
        this.userHandler = userHandler;
        this.categoryHandler = categoryHandler;
        this.transactionHandler = transactionHandler;
        this.budgetHandler = budgetHandler;
    }

    public Object getHandler(String name) {
        return switch (name.toLowerCase()) {
            case "user" -> userHandler;
            case "category" -> categoryHandler;
            case "transaction" -> transactionHandler;
            case "budget" -> budgetHandler;
            default -> throw new IllegalArgumentException("❌ Unbekannter Handler: " + name);
        };
    }
}
