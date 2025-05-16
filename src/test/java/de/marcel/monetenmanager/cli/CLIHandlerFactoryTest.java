package de.marcel.monetenmanager.cli;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.marcel.monetenmanager.cli.budget.BudgetCLIHandler;
import de.marcel.monetenmanager.cli.category.CategoryCLIHandler;
import de.marcel.monetenmanager.cli.transaction.TransactionCLIHandler;
import de.marcel.monetenmanager.cli.user.UserCLIHandler;

public class CLIHandlerFactoryTest {

    private CLIHandlerFactory factory;

    @BeforeEach
    void setUp() {
        Scanner dummyScanner = new Scanner(System.in);

        UserCLIHandler user = new UserCLIHandler(null, null, dummyScanner);
        CategoryCLIHandler category = new CategoryCLIHandler(null, dummyScanner);
        TransactionCLIHandler transaction = new TransactionCLIHandler(null, dummyScanner);
        BudgetCLIHandler budget = new BudgetCLIHandler(null, null, dummyScanner);

        factory = new CLIHandlerFactory(user, category, transaction, budget);
    }

    @Test
    void testReturnsCorrectHandlerType() {
        assertTrue(factory.getHandler("user") instanceof UserCLIHandler);
        assertTrue(factory.getHandler("category") instanceof CategoryCLIHandler);
        assertTrue(factory.getHandler("transaction") instanceof TransactionCLIHandler);
        assertTrue(factory.getHandler("budget") instanceof BudgetCLIHandler);
    }

    @Test
    void testThrowsExceptionForUnknownHandler() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                factory.getHandler("unknown"));
        assertTrue(ex.getMessage().contains("Unbekannter Handler"));
    }
}

