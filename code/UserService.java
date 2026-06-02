import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> users;

    public UserService() {

        users = new ArrayList<>();

        users.add(
                new User(
                        "student",
                        "1234",
                        Role.STUDENT));

        users.add(
                new User(
                        "dozent",
                        "1234",
                        Role.DOZENT));
    }

    public User login(String username,
                      String password) {

        for (User user : users) {

            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {

                return user;
            }
        }

        return null;
    }
}