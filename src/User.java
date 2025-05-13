package main.java.investWise;

import java.io.Serializable;
import java.util.ArrayList;
import main.java.investWise.assets.Asset;
import main.java.investWise.assets.AssetManager;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    String email;
    String username;
    String password;
    private ArrayList<Account> accounts;
    private ArrayList<Asset> assets;
    public AssetManager assetManager;

    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<>();
        this.assets = new ArrayList<>();
        this.assetManager = new AssetManager(this.assets);
    }

    public boolean login(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public static User register(String name, String email, String username, String password) {
        return new User(name, email, username, password);
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

}
