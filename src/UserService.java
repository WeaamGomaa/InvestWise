package main.java.investWise;

import java.io.*;
import java.util.List;
import java.util.ArrayList;


public class UserService {
    private static final String FILENAME = "users_list.ser";
    private static final String DATA_FILE = "users.dat";
    private List<User> users = new ArrayList<>();

    public UserService() {
        try (FileInputStream fis = new FileInputStream(FILENAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            users = (List<User>) ois.readObject();
        }
        catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public void saveUsers() {
        try (FileOutputStream fos = new FileOutputStream(DATA_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this.users);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Boolean registerUser(String name, String email, String username, String password) {
        if (getUser(username) != null) {
            System.out.println("Username already exists!");
            return false;
        }

        users.add(User.register(name, email, username, password));
        return true;
    }

    public User authenticate(String username, String password) {
        User user = getUser(username);
        if (user == null) {
            System.out.println("User not found!");
            return null;
        }
        return (user.login(password)) ? user : null;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
