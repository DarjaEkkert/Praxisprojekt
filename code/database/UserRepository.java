package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Role;
import model.User;

public class UserRepository {

    private DatabaseManager databaseManager;

    public UserRepository(
        DatabaseManager databaseManager) {

        this.databaseManager = databaseManager;
    }

    public User getUserByUsername(
        String username) {

    try {

        String sql =
                "SELECT * FROM users "
                + "WHERE username = ?";

        PreparedStatement statement =
                databaseManager
                        .getConnection()
                        .prepareStatement(sql);

        statement.setString(
                1,
                username);

        ResultSet result =
                statement.executeQuery();

        if (result.next()) {

            String password =
                    result.getString(
                            "password");

            Role role =
                    Role.valueOf(
                            result.getString(
                                    "role"));

            return new User(
                    username,
                    password,
                    role);
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return null;
}

public void saveUser(User user) {

    try {

        String sql =
                "INSERT INTO users(username, password, role) "
                + "VALUES (?, ?, ?)";

        PreparedStatement statement =
                databaseManager
                        .getConnection()
                        .prepareStatement(sql);

        statement.setString(
                1,
                user.getUsername());

        statement.setString(
                2,
                user.getPassword());

        statement.setString(
                3,
                user.getRole().name());

        statement.executeUpdate();

    } catch (Exception e) {

        e.printStackTrace();
    }
}
}