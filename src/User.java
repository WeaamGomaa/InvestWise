package main.java.investWise;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    String email;
    String username;
    String password;

    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
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
}
