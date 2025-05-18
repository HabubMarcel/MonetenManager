package de.marcel.monetenmanager.cli;

import de.marcel.monetenmanager.cli.budget.BudgetCLIHandler;
import de.marcel.monetenmanager.cli.category.CategoryCLIHandler;
import de.marcel.monetenmanager.cli.transaction.TransactionCLIHandler;
import de.marcel.monetenmanager.cli.user.OverviewCLIHandler;
import de.marcel.monetenmanager.cli.user.UserCLIHandler;
import de.marcel.monetenmanager.service.category.CategoryService;
import de.marcel.monetenmanager.service.user.UserLoginService;
import de.marcel.monetenmanager.service.user.UserRegistrationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CLIHandlerFactoryTest {

    private CLIHandlerFactory factory;

    @BeforeEach
    public void setUp() {
        Scanner scanner = new Scanner(System.in);

        UserCLIHandler userHandler = new UserCLIHandler(
        mock(UserRegistrationService.class),
        mock(UserLoginService.class),
        mock(CategoryService.class),
        new Scanner(System.in)
    );

        var transactionHandler = new TransactionCLIHandler(null, null, scanner);
        var categoryHandler = new CategoryCLIHandler(null, scanner);
        var budgetHandler = new BudgetCLIHandler(null, null, scanner);
        var overviewHandler = new OverviewCLIHandler(null, scanner);

        factory = new CLIHandlerFactory(
            userHandler,
            transactionHandler,
            categoryHandler,
            budgetHandler,
            overviewHandler
        );
    }


    @Test
    public void testFactoryReturnsCorrectHandlers() {
        assertNotNull(factory.userHandler());
        assertNotNull(factory.transactionHandler());
        assertNotNull(factory.categoryHandler());
        assertNotNull(factory.budgetHandler());
        assertNotNull(factory.overviewHandler());
    }
}
