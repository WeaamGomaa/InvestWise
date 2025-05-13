package main.java.investWise.assets;

import main.java.investWise.assets.strategies.GoldStrategy;
import main.java.investWise.assets.strategies.InvestmentStrategy;
import main.java.investWise.assets.strategies.RealEstateStrategy;
import main.java.investWise.assets.strategies.StockStrategy;
import main.java.investWise.observers.Dashboard;
import main.java.investWise.observers.Observer;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

public class AssetManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private InvestmentStrategy strategy;
    private ArrayList<Asset> assets = new ArrayList<>();
    private Observer dashboard;

    public AssetManager() {
        this.dashboard = new Dashboard();
    }

    public AssetManager(ArrayList<Asset> assets) {
        this.assets = assets;
        this.dashboard = new Dashboard();
    }

    private void ensureObserverInitialized() {
        if (dashboard == null) {
            dashboard = new Dashboard();
        }
    }

    public void printAllAssets() {
        System.out.println("\n******** CURRENT PORTFOLIO ********");
        for (Asset asset : assets) {
            System.out.println(asset.toString());
        }
        System.out.println("*************************************");
    }

    //*************Add Asset****************
    public void addAsset(String assetName, String assetType, HashMap<String, Object> assetDetails){
        if(assetDetails == null || assetName.isEmpty() || assetType.isEmpty()){
            System.out.println("Asset name, type, and details are required.");
        }

        //************Select Strategy*************
        switch (assetType.toLowerCase()){
            case "stocks":
                strategy = new StockStrategy();
                break;
            case "real estate":
                strategy = new RealEstateStrategy();
                break;
            case "gold":
                strategy = new GoldStrategy();
                break;
            default:
                System.out.println("Unsupported asset type: "+ assetType);
        }
        this.setStrategy(strategy);

        //*********Create Asset Object***********
        Asset newAsset = new Asset(assetName,assetType);

        newAsset.setQuantity((int) assetDetails.get("quantity"));
        newAsset.setPurchaseDate((LocalDate) assetDetails.get("purchaseDate"));
        newAsset.setPurchasePrice((double) assetDetails.get("purchasePrice"));

        //Record initial values
//        newAsset.recordChange("Initial_quantity", null, assetDetails.get("quantity"));
//        newAsset.recordChange("Initial_price", null, assetDetails.get("purchasePrice"));

        //************Calculate Value and Check Halal Status************
        double value = strategy.calculateValue(newAsset);
        checkHalal(newAsset);

        newAsset.setCurrentValue(value);
        ensureObserverInitialized();
        newAsset.addObserver(dashboard);
        newAsset.notifyObserver();

        // add to portfolio
        assets.add(newAsset);
        System.out.println("Asset created. History:");
        newAsset.getUpdateHistory().forEach(System.out::println);
    }


    //***********Edit Asset**************
    public void editAsset(int index, String newName,
                          int newQuantity, double newPrice, LocalDate newDate){
        Asset asset = assets.get(index - 1);

        // Apply changes after checks
        if(newName != null){
            asset.Name = newName;
        }

        if(newQuantity > 0 ){
            asset.setQuantity(newQuantity);
        } else if(newQuantity == -1){
            asset.setQuantity(asset.quantity);
        }

        if(newPrice > 0){
            asset.setPurchasePrice(newPrice);
        } else if(newPrice == -1){
            asset.setPurchasePrice(asset.purchasePrice);
        }

        if(newDate != null){
            asset.setPurchaseDate(newDate);
        }

//        asset.setName(newName);         // Auto-records if changed
//        asset.setQuantity(newQuantity);      // (Modify setter like setName)
//        asset.setPurchasePrice(newPrice);
//        asset.setPurchaseDate(newDate);

        // Recalculate and notify
        asset.setCurrentValue(strategy.calculateValue(asset));
        asset.notifyObserver();
    }

    //**************Remove Asset**************
    public boolean removeAsset(int index){
        if(index == 0){
            System.out.println("Removing canceled!");
            return false;
        }
        if (index - 1 < 0 || index - 1 >= assets.size()){
            System.out.println("Invalid asset number!");
            return false;
        }
        else {
            System.out.println("Confirm remove " + getAssets().get(index - 1).Name + " ? (y/n)");
            Scanner scanner =  new Scanner(System.in);
            String confirmation = scanner.nextLine();
            if(confirmation.equals("y") || confirmation.equals("Y")){
                Asset assetToRemove = assets.get(index - 1);
                assetToRemove.recordChange("Removed", assetToRemove.toString(), null);
                assets.remove(index - 1);
                return true;
            }
            else{
                return false;
            }
        }
    }

    public boolean isValidIndex(int index){
        return index >= 0 && index < assets.size();
    }

    public void setStrategy(InvestmentStrategy strategy){
        this.strategy = strategy;
    }

    public double getAssetValue(Asset asset) {
        return strategy.calculateValue(asset);
    }

    public boolean checkHalal(Asset asset) {
        return strategy.isHalal(asset);
    }

    //***************Save Portfolio to File
    public void savePortfolio(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(this);  // Serialize entire AssetManager
            System.out.println("Portfolio saved to " + filename);
        } catch (IOException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    //****************Load Portfolio from File
    public static AssetManager loadPortfolio(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            AssetManager manager = (AssetManager) ois.readObject();
            manager.ensureObserverInitialized(); // <-- Reinitialize
            for (Asset asset : manager.assets) {
                asset.addObserver(manager.dashboard); // Re-link
            }  // Restore observers

            System.out.println("Portfolio loaded from " + filename);
            return manager;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Load failed: " + e.getMessage());
            return new AssetManager();  // Fallback to empty portfolio
        }
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public InvestmentStrategy getStrategy() {
        return strategy;
    }
}
