public class UserService {
    private static final String FILENAME = "users_list.ser";
    private List<User> users = new ArrayList<>();

    public UserService() {
        List<User> loadedUsers;
        try (FileInputStream fis = new FileInputStream(FILENAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            loadedUsers = (List<UltraSimpleUserStorage.User>) ois.readObject(); // Deserialize
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

    public void registerUser(String name, String email, String username, String password) {

    }
}







