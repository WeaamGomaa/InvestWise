package main.java.investWise;

import main.java.investWise.assets.Asset;
import main.java.investWise.assets.AssetManager;
import main.java.investWise.assets.strategies.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuHandler {
    private AssetManager manager = new AssetManager();
    private UserService userService = new UserService();
    private Scanner scanner = new Scanner(System.in);
    private User currentUser = null;

    private double promptForDouble(String message) {
        while (true) {
            try {
                System.out.println(message);
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value <= 0) {
                    System.out.println("Value must be greater than 0");
                    continue;
                }
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid number format");
                scanner.nextLine();
            }
        }
    }

    private LocalDate promptForDate(String message) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            try {
                System.out.println(message + "[YYYY-MM-DD]: ");
                String input = scanner.nextLine().trim();
                return LocalDate.parse(input, dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, please use YYYY-MM-DD");
            }
        }
    }

    private int promptForInt(String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                int input = scanner.nextInt();
                scanner.nextLine();
                if (input >= min && input <= max) {
                    return input;
                } else if (input == 0) {
                    System.out.println("Editing canceled!");
                    return input;
                } else {
                    System.out.printf("Input must be between %d and %d.%n", min, max);
                }
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Clear invalid input
                System.out.println("Invalid input, please enter a correct number.");
            }
        }
    }

    public void showLoginSignupMenu() {
        while (currentUser == null) {
            System.out.println("=== InvestWise ===");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    currentUser = userService.authenticate(username, password);
                    if (currentUser != null) {
                        System.out.println("Login successful!");
                        showMainMenu();
                    } else {
                        System.out.println("Login failed. Invalid credentials.");
                    }
                    break;
                case 2:
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Password: ");
                    String newPassword = scanner.nextLine();
                    if (userService.registerUser(name, email, newUsername, newPassword)) {
                        System.out.println("Registration successful! Please login.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting InvestWise. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void showMainMenu() {
        System.out.println("********** Welcome to InvestWise Program **********");
        System.out.println("    A Place Where Investing Couldn't Be Easier!");
        System.out.println("***************************************************");

        while (true) {
            System.out.println("1- Asset Management" +
                    "\n2- Financial Goals" +
                    "\n3- Risk & Allocation" +
                    "\n4- Zakat & Compliance" +
                    "\n5- Reports" +
                    "\n6- Save Portfolio" +
                    "\n7- Load Portfolio" +
                    "\n8- Logout");
            System.out.print("Enter your choice please: ");
            int mainMenuChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainMenuChoice) {
                case 1:
                    showAssetManagementMenu();
                    break;
                case 5:
                    ReportGenerator.showReportMenu(manager);
                    break;
                case 6:
                    System.out.print("Enter filename to save: ");
                    manager.savePortfolio(scanner.nextLine());
                    break;
                case 7:
                    System.out.print("Enter filename to load: ");
                    manager = AssetManager.loadPortfolio(scanner.nextLine());
                    printPortfolioContents();
                    break;
                case 8:
                    System.out.println("Logging out...");
                    currentUser = null; // Reset user to go back to login menu
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void showAssetManagementMenu() {
        while (true) {
            System.out.println("*****ASSET MANAGEMENT PAGE*****");
            System.out.println("1- Add Asset" +
                    "\n2- Edit Asset" +
                    "\n3- Remove Asset" +
                    "\n4- Assets List" +
                    "\n5- Return to Main Menu");
            System.out.print("Enter your choice please: ");
            int subMenuChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (subMenuChoice) {
                case 1:
                    handleAddAsset();
                    break;
                case 2:
                    handleEditAsset();
                    break;
                case 3:
                    handleRemoveAsset();
                    break;
                case 4:
                    displayAssetsList();
                    break;
                case 5:
                    return; // Go back to the showMainMenu
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private void handleAddAsset() {
        System.out.println("*****Add Assets*****");
        System.out.println("1- Stocks\n2- Real Estate\n3- Gold");
        System.out.print("Choose Asset type: (1-3) ");
        String assetTypeInput;
        switch (scanner.nextInt()) {
            case 1:
                assetTypeInput = "stocks";
                break;
            case 2:
                assetTypeInput = "real estate";
                break;
            case 3:
                assetTypeInput = "gold";
                break;
            default:
                System.out.println("Invalid type, defaulting to Stocks");
                assetTypeInput = "stocks";
        }
        scanner.nextLine();

        System.out.print("Name: ");
        String assetNameInput = scanner.nextLine();
        while (assetNameInput.length() > 100) {
            System.out.print("Name must be less than 100 characters, try again: ");
            assetNameInput = scanner.nextLine();
        }

        HashMap<String, Object> assetDetailsInput = new HashMap<>();
        assetDetailsInput.put("quantity", (int) promptForDouble("Quantity: "));
        assetDetailsInput.put("purchaseDate", promptForDate("Purchase Date: "));
        assetDetailsInput.put("purchasePrice", promptForDouble("Purchase Price"));

        manager.addAsset(assetNameInput, assetTypeInput, assetDetailsInput);
        System.out.println("Asset added successfully!");
        printAllAssets();
    }

    private void handleEditAsset() {
        if (manager.getAssets().isEmpty()) {
            System.out.println("No assets to edit");
            return;
        }

        printPortfolioContents();

        int index = promptForInt("Enter asset number to edit (0 to cancel)", 1, manager.getAssets().size());
        if (index == 0) {
            return;
        }

        Asset assetToEdit = manager.getAssets().get(index - 1);

        System.out.print("New name (leave empty to keep '" + assetToEdit.Name + "'): ");
        String newName = scanner.nextLine();

        System.out.print("New quantity (enter 0 to keep '" + assetToEdit.quantity + "'): ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("New price (enter 0 to keep '" + assetToEdit.purchasePrice + "'): ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine();

        LocalDate newDate = promptForDate("New purchase date (leave empty to keep '" + assetToEdit.purchaseDate + "') [YYYY-MM-DD]: ");

        manager.editAsset(
                index,
                newName.isEmpty() ? assetToEdit.Name : newName,
                newQuantity == 0 ? -1 : newQuantity,
                newPrice == 0 ? -1 : newPrice,
                newDate == null ? assetToEdit.purchaseDate : newDate
        );
        System.out.println("Asset updated!");
        showHistory(index - 1);
    }

    private void handleRemoveAsset() {
        if (manager.getAssets().isEmpty()) {
            System.out.println("No assets to remove!");
            return;
        }

        printPortfolioContents();

        int index = promptForInt("Enter asset number to remove (0 to cancel)", 1, manager.getAssets().size());
        if (index == 0) {
            return;
        }

        if (manager.removeAsset(index)) {
            System.out.println("Asset removed!");
        } else {
            System.out.println("Asset not removed!");
        }
        showHistory(index - 1);
    }

    private void displayAssetsList() {
        printAllAssets();
    }

    public void printPortfolioContents() {
        System.out.println("\n************ PORTFOLIO CONTENTS ************");
        if (manager.getAssets().isEmpty()) {
            System.out.println("There are no assets in portfolio");
        }
        int index = 1;
        for (Asset asset : manager.getAssets()) {
            System.out.printf("%d. %s\n", index++, asset.toString());
            System.out.printf(" - Current Value: $%.2f\n", manager.getStrategy().calculateValue(asset));
            System.out.printf(" - Halal Status: %s\n", manager.checkHalal(asset));
        }
        System.out.println("*********************************************");
    }

    private void printAllAssets() {
        manager.printAllAssets();
    }

    private void showHistory(int assetIndex) {
        if (assetIndex >= 0 && assetIndex < manager.getAssets().size()) {
            Asset asset = manager.getAssets().get(assetIndex);
            if (asset.getUpdateHistory() == null || asset.getUpdateHistory().isEmpty()) {
                System.out.println("No history available for this asset");
                return;
            }
            System.out.println("\n=== Update History for " + asset.Name + " ===");
            asset.getUpdateHistory().forEach(System.out::println);
        } else {
            System.out.println("Invalid asset index.");
        }
    }
}
