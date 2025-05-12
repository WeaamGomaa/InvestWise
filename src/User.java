import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L; // Version control

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

    public String getUsername() {
        return username;
    }
}
