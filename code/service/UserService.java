package service;

import model.User;
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
// für tests
public UserService(UserRepository userRepository,
                   DatabaseManager databaseManager) {

    this.userRepository = userRepository;
    this.databaseManager = databaseManager;
}

public User login(String username,
                  String password) {

    User user =
            userRepository.getUserByUsername(username);

    databaseManager.disconnect();

    if (user != null
            && user.getPassword().equals(password)) {

        return user;
    }

    return null;
}
}