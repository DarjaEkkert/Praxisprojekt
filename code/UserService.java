import model.Role;
import model.User;

import java.util.ArrayList;

import database.DatabaseManager;
import database.UserRepository;

public class UserService {

    private UserRepository userRepository;
    private DatabaseManager databaseManager;
    

    public UserService() {

        databaseManager = new DatabaseManager();

        databaseManager.connect();

        userRepository = new UserRepository(databaseManager);
}

    public User login(String username,
                      String password) {

        User user =
        userRepository
                .getUserByUsername(
                        username);

        if (user != null
                && user.getPassword()
                    .equals(password)) {
            databaseManager.disconnect();
            return user;
        }

        databaseManager.disconnect();
        return null;
    }
}