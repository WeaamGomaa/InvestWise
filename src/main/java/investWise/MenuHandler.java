package main.java.investWise;

//import main.java.investWise.assets.Asset;
import main.java.investWise.assets.Asset;
import main.java.investWise.assets.AssetManager;
//import main.java.investWise.observers.Observer;
//import main.java.investWise.observers.Dashboard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;


public class MenuHandler {
    private AssetManager manager = new AssetManager();
    private double promptForDouble(String message){
        Scanner scanner = new Scanner(System.in);

        while (true){
            try{
                System.out.println(message);
                double value = scanner.nextDouble();
                scanner.nextLine();

                if(value <= 0){
                    System.out.println("Value must be greater than 0");
                    continue;
                }
                return value;
            } catch (InputMismatchException e){
                System.out.println("Invalid number format");
                scanner.nextLine();
            }
        }
    }
    private LocalDate promptForDate(String message){
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (true){
            try{
                System.out.println(message + "[YYYY-MM-DD]: ");
                String input = scanner.nextLine().trim();
                return LocalDate.parse(input, dateFormat);

            } catch (DateTimeParseException e){
                System.out.println("Invalid date format, please use YYYY-MM-DD");
            }
        }
    }

    private int promptForInt(String message, int min, int max) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(message);
                int input = scanner.nextInt();
                scanner.nextLine();
                if (input >= min && input <= max) {
                    return input;
                } else if(input == 0) {
                    System.out.println("Editing canceled!");
                    return input;
                } else{
                    System.out.printf("Input must be between %d and %d.%n", min, max);
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();  // Clear invalid input
                System.out.println("Invalid input, please enter a correct number.");
            }
        }
    }

//    private String promptForString(String message, boolean allowEmpty){
//
//    }

    public void showMainMenu(AssetManager manager){
        System.out.println("********** Welcome to InvestWise Program **********");
        System.out.println("       A Place Where Investing Couldn't Be Easier!");
        System.out.println("***************************************************");

        while(true){
            System.out.println("1- Asset Management" +
                    "\n2- Financial Goals" +
                    "\n3- Risk & Allocation" +
                    "\n4- Zakat & Compliance" +
                    "\n5- Reports" +
                    "\n6- Save Portfolio" +
                    "\n7- Load Portfolio" +
                    "\n8- Exit");
            System.out.println("Enter your choice please: ");
            Scanner userInput = new Scanner(System.in);
            int mainMenuChoice = userInput.nextInt();
            userInput.nextLine();

            switch (mainMenuChoice){
                case 1:
                    showAssetManagementMenu(manager);
                    break;
                case 6:
                    System.out.println("Enter filename to save: ");
                    manager.savePortfolio(userInput.nextLine());
                    break;
                case 7:
                    System.out.println("Enter filename to load: ");
                    manager = AssetManager.loadPortfolio(userInput.nextLine());
                    printPortfolioContents(manager);
                    break;
                case 8:
                    System.out.println("Thank you for using InvestWise!");
                    continue;
                default:
                    System.out.println("Invalid choice");
            }
            if(mainMenuChoice == 8){
                break;
            }
        }


    }
    public void showAssetManagementMenu(AssetManager manager){
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("*****ASSET MANAGEMENT PAGE*****");
            System.out.println("1- Add Asset" +
                    "\n2- Edit Asset" +
                    "\n3- Remove Asset" +
                    "\n4- Assets List" +
                    "\n5- Return to Main Menu");
            System.out.println("Enter your choice please: ");
            int subMenuChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (subMenuChoice){
                case 1:
                    handleAddAsset(manager);
                    break;
                case 2:
                    handleEditAsset(manager);
                    break;
                case 3:
                    handleRemoveAsset(manager);
                    break;
                case 4:
                    displayAssetsList(manager);
                    break;
                case 5:
                    showMainMenu(manager);
                    break;
                    //return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
    private void handleAddAsset(AssetManager manager){
        Scanner scanner = new Scanner(System.in);

        System.out.println("*****Add Assets*****");
        //Select asset type
        System.out.println("1- Stocks" +
                "\n2- Real Estate" +
                "\n3- Gold");
        System.out.println("Choose Asset type: (1-3)");
        String assetTypeInput;
        switch (scanner.nextInt()){
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

        //Get asset name
        System.out.println("Name: ");
        String assetNameInput = scanner.nextLine();
        while (assetNameInput.length() > 100){
            System.out.println("Name must be less than 100 characters, try again: ");
            assetNameInput = scanner.nextLine();
        }

        //Get asset's details
        HashMap<String, Object> assetDetailsInput = new HashMap<String, Object>();
        assetDetailsInput.put("quantity", (int) promptForDouble("Quantity: "));
        assetDetailsInput.put("purchaseDate", promptForDate("Purchase Date: "));
        assetDetailsInput.put("purchasePrice", promptForDouble("Purchase Price"));

        //Add asset through AssetManager
        manager.addAsset(assetNameInput, assetTypeInput, assetDetailsInput);
        System.out.println("Asset added successfully!");
        manager.printAllAssets();
    }

    private void handleEditAsset(AssetManager manager){
        Scanner scanner = new Scanner(System.in);
        if(manager.getAssets().isEmpty()){
            System.out.println("No assets to edit");
        }

        //List assets
        printPortfolioContents(manager);

        //Select asset
        int index = promptForInt("Enter asset number to edit (0 to cancel)", 1, manager.getAssets().size());
        if (index - 1 == -1){
            return;
        }

        //Get the new values
        System.out.println("New name: ");
        String newName = scanner.nextLine();
        scanner.nextLine();

        System.out.println("New quantity (Enter 0 to keep): ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine();

//        double newPrice = promptForDouble("New price (Enter 0 to keep): ");
        System.out.println("New price (Enter 0 to keep): ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine();

        LocalDate newDate = promptForDate("New date [YYYY-MM-DD]: ");


        //Call AssetManager
        manager.editAsset(
                index,
                newName.isEmpty() ? manager.getAssets().get(index - 1).Name : newName,
                newQuantity == 0 ? -1 : newQuantity,
                newPrice == 0 ? -1 : newPrice,
                newDate);
        System.out.println("Asset updated!");

        showHistory(manager);
    }

    private void handleRemoveAsset(AssetManager manager){
        if (manager.getAssets().isEmpty()) {
            System.out.println("No assets to remove!");
        }

        //List assets
        printPortfolioContents(manager);

        //Select asset
        int index = promptForInt("Enter asset number to remove (0 to cancel)", 1, manager.getAssets().size());
        if (index - 1 == -1){
            return;
        }

        //remove asset
        if (manager.removeAsset(index)){
            System.out.println("Asset removed!");
        }
        else{
            System.out.println("Asset not removed!");
        }

        showHistory(manager);
    }

    private void displayAssetsList(AssetManager manager){
        manager.printAllAssets();
    }

    public void printPortfolioContents(AssetManager manager){
        System.out.println("\n************ PORTFOLIO CONTENTS ************");

        if(manager.getAssets().isEmpty()){
            System.out.println("There are no assets in portfolio");
        }

        // print each asset with index number
        int index = 1;
        for(Asset asset : manager.getAssets()){
            System.out.printf("%d. %s\n", index++, asset.toString());
            System.out.printf(" - Current Value: $%.2f\n", manager.getStrategy().calculateValue(asset));
            System.out.printf(" - Halal Status: %s\n", manager.checkHalal(asset));
        }
        System.out.println("*********************************************");
    }

    private void showHistory(AssetManager manager) {
        int index = promptForInt("View history for asset (1-" + (manager.getAssets().size()) + "): ",
                1, manager.getAssets().size());
        if (manager.getAssets().get(index - 1) == null || manager.getAssets().get(index -1).getUpdateHistory() == null) {
            System.out.println("No history available for this asset");
            return;
        }
        System.out.println("\n=== Update History ===");
        manager.getAssets().get(index - 1).getUpdateHistory().forEach(System.out::println);
    }
}
