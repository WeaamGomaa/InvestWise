package main.java.investWise;

//import main.java.investWise.assets.Asset;
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

    public void showMainMenu(){
        System.out.println("***********Welcome At InvestWise Program***********");
        System.out.println("       A Place Where You Can Invest Easily!");
        System.out.println("***************************************************");
        System.out.println("1- Asset Management" +
                "\n2- Financial Goals" +
                "\n3- Risk & Allocation" +
                "\n4- Zakat & Compliance" +
                "\n5- Reports" +
                "\n6- Exit");
        System.out.println("Enter your choice please: ");
        Scanner userInput = new Scanner(System.in);
        int mainMenuChoice = userInput.nextInt();

        switch (mainMenuChoice){
            case 1:
                showAssetManagementMenu(manager);
                break;
            default:
                System.out.println("Invalid choice");
        }

    }
    public void showAssetManagementMenu(AssetManager manager){
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("*****ASSET MANAGEMENT PAGE*****");
            System.out.println("1- Add Asset" +
                    "\n2- Edit/Remove Asset" +
                    "\n3- Assets List" +
                    "\n4- Return to Main Menu");
            System.out.println("Enter your choice please: ");
            int subMenuChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (subMenuChoice){
                case 1:
                    handleAddAsset(manager);
                    break;
                case 2:
                    handleEditRemoveAsset(manager);
                    break;
                case 3:
                    displayAssetsList(manager);
                    break;
                case 4:
                    return;
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
    private void handleEditRemoveAsset(AssetManager manager){

    }
    private void displayAssetsList(AssetManager manager){
        manager.printAllAssets();
    }
}
