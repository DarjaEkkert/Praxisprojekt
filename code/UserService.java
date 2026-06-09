import model.Role;
import model.User;

import java.util.ArrayList;

import database.DatabaseManager;
import database.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService() {

        DatabaseManager databaseManager = new DatabaseManager();

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

            return user;
        }

        return null;
    }
}