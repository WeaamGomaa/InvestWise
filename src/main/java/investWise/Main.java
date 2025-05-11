package main.java.investWise;

//import main.java.investWise.assets.Asset;
//import main.java.investWise.assets.AssetManager;
//import main.java.investWise.observers.Observer;
//import main.java.investWise.observers.Dashboard;
//import main.java.investWise.MenuHandler;

import main.java.investWise.assets.AssetManager;

public class Main {
    public static void main(String[] args) {
        AssetManager manager = new AssetManager();
        MenuHandler menuHandler = new MenuHandler();
        menuHandler.showMainMenu(manager);
    }
}