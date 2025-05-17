package de.marcel.monetenmanager.main;

import de.marcel.monetenmanager.application.budget.BudgetService;
import de.marcel.monetenmanager.application.category.CategoryService;
import de.marcel.monetenmanager.application.transaction.TransactionService;
import de.marcel.monetenmanager.application.user.UserLoginService;
import de.marcel.monetenmanager.application.user.UserRegistrationService;
import de.marcel.monetenmanager.cli.CLIHandlerFactory;
import de.marcel.monetenmanager.cli.budget.BudgetCLIHandler;
import de.marcel.monetenmanager.cli.category.CategoryCLIHandler;
import de.marcel.monetenmanager.cli.transaction.TransactionCLIHandler;
import de.marcel.monetenmanager.cli.user.OverviewCLIHandler;
import de.marcel.monetenmanager.cli.user.UserCLIHandler;
import de.marcel.monetenmanager.domain.user.MonthlyOverviewService;
import de.marcel.monetenmanager.domain.user.User;
import de.marcel.monetenmanager.infrastructure.budget.BudgetJpaRepository;
import de.marcel.monetenmanager.infrastructure.budget.DatabaseBudgetRepository;
import de.marcel.monetenmanager.infrastructure.category.CategoryJpaRepository;
import de.marcel.monetenmanager.infrastructure.category.DatabaseCategoryRepository;
import de.marcel.monetenmanager.infrastructure.transaction.DatabaseTransactionRepository;
import de.marcel.monetenmanager.infrastructure.transaction.TransactionJpaRepository;
import de.marcel.monetenmanager.infrastructure.user.DatabaseUserRepository;
import de.marcel.monetenmanager.infrastructure.user.UserJpaRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Scanner;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "de.marcel.monetenmanager.infrastructure")
@EntityScan(basePackages = "de.marcel.monetenmanager.infrastructure")
@ComponentScan(basePackages = "de.marcel.monetenmanager")
public class MonetenmanagerApplication implements CommandLineRunner {

    private final UserJpaRepository userJpaRepository;
    private final TransactionJpaRepository transactionJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final BudgetJpaRepository budgetJpaRepository;

    public MonetenmanagerApplication(UserJpaRepository userJpaRepository,
                                     TransactionJpaRepository transactionJpaRepository,
                                     CategoryJpaRepository categoryJpaRepository,
                                     BudgetJpaRepository budgetJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.transactionJpaRepository = transactionJpaRepository;
        this.categoryJpaRepository = categoryJpaRepository;
        this.budgetJpaRepository = budgetJpaRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(MonetenmanagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        // Repositories
        var userRepository = new DatabaseUserRepository(userJpaRepository);
        var transactionRepository = new DatabaseTransactionRepository(transactionJpaRepository);
        var categoryRepository = new DatabaseCategoryRepository(categoryJpaRepository);
        var budgetRepository = new DatabaseBudgetRepository(budgetJpaRepository);

        // Services
        var registrationService = new UserRegistrationService(userRepository);
        var loginService = new UserLoginService(userRepository);
        var transactionService = new TransactionService(transactionRepository);
        var categoryService = new CategoryService(categoryRepository);
        var budgetService = new BudgetService(budgetRepository);
        var overviewService = new MonthlyOverviewService(transactionRepository, categoryRepository, budgetRepository);

        // CLI Handler Factory
        var userCLIHandler = new UserCLIHandler(registrationService, loginService, categoryService, scanner);
        var transactionCLIHandler = new TransactionCLIHandler(transactionService, categoryService, scanner);
        var categoryCLIHandler = new CategoryCLIHandler(categoryService, scanner);
        var budgetCLIHandler = new BudgetCLIHandler(budgetService, categoryService, scanner);
        var overviewCLIHandler = new OverviewCLIHandler(overviewService, scanner);

        var cliHandlerFactory = new CLIHandlerFactory(
                userCLIHandler,
                transactionCLIHandler,
                categoryCLIHandler,
                budgetCLIHandler,
                overviewCLIHandler
        );

        User currentUser = null;

        while (true) {
            System.out.println("\n==== MonetenManager ====");
            System.out.println("[1] Benutzer registrieren");
            System.out.println("[2] Einloggen");
            if (currentUser != null) {
                System.out.println("[3] Transaktion hinzufügen");
                System.out.println("[4] Transaktionen anzeigen");
                System.out.println("[5] Kategorie erstellen");
                System.out.println("[6] Kategorien anzeigen");
                System.out.println("[7] Budget erstellen");
                System.out.println("[8] Budgets anzeigen");
                System.out.println("[9] Monatsübersicht anzeigen");
            }
            System.out.println("[0] Beenden");
            System.out.print("Auswahl: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> userCLIHandler.handleRegistration();
                case "2" -> {
                    currentUser = cliHandlerFactory.userHandler().handleLogin();
                    if (currentUser != null) {
                        System.out.println("➡️ Eingeloggt als " + currentUser.getName());
                    }
                }
                case "3" -> {
                    if (currentUser != null)
                        cliHandlerFactory.transactionHandler().handleAddTransaction(currentUser.getId());
                    else System.out.println("Bitte zuerst einloggen.");
                }
                case "4" -> {
                    if (currentUser != null)
                        cliHandlerFactory.transactionHandler().handleListTransactions(currentUser.getId());
                    else System.out.println("Bitte zuerst einloggen.");
                }
                case "5" -> {
                    if (currentUser != null)
                        cliHandlerFactory.categoryHandler().handleCreateCategory(currentUser.getId());
                    else System.out.println("Bitte zuerst einloggen.");
                }
                case "6" -> {
                    if (currentUser != null)
                        cliHandlerFactory.categoryHandler().handleListCategories(currentUser.getId());
                    else System.out.println("Bitte zuerst einloggen.");
                }
                case "7" -> {
                    if (currentUser != null)
                        cliHandlerFactory.budgetHandler().handleCreateBudget(currentUser.getId());
                    else System.out.println("Bitte zuerst einloggen.");
                }
                case "8" -> {
                    if (currentUser != null)
                        cliHandlerFactory.budgetHandler().handleListBudgets(currentUser.getId());
                    else System.out.println("Bitte zuerst einloggen.");
                }
                case "9" -> {
                    if (currentUser != null)
                        cliHandlerFactory.overviewHandler().handleOverview(currentUser.getId());
                    else System.out.println("Bitte zuerst einloggen.");
                }
                case "0" -> {
                    System.out.println("👋 Bis bald!");
                    return;
                }
                default -> System.out.println("❌ Ungültige Auswahl.");
            }
        }
    }
}
