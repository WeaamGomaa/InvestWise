package main.java.investWise;

import main.java.investWise.assets.Asset;
import main.java.investWise.assets.AssetManager;

import java.io.*;
import java.util.ArrayList;

public class PortfolioService {

    public static void savePortfolio(User user, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(user.getAssets()); // Save the user's list of assets
            System.out.println("Portfolio saved for user " + user.getUsername() + " to " + filename);
        } catch (IOException e) {
            System.out.println("Save failed for user " + user.getUsername() + ": " + e.getMessage());
        }
    }

    public static void loadPortfolio(User user, String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            ArrayList<Asset> loadedAssets = (ArrayList<Asset>) ois.readObject();
            user.getAssets().clear();
            user.getAssets().addAll(loadedAssets);
            System.out.println("Portfolio loaded for user " + user.getUsername() + " from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Load failed for user " + user.getUsername() + ": " + e.getMessage());
            user.assetManager = new AssetManager(new ArrayList<>()); // Ensure AssetManager exists even if load fails
        }
    }
}
