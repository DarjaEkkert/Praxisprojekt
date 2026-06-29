package test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import database.DatabaseManager;
import database.UserRepository;
import model.Role;
import model.User;
import service.UserService;

public class UserServiceMockTest {

    @Test
    void sollteLoginMitGemocktemRepositoryErfolgreichPruefen() {

        UserRepository repository = mock(UserRepository.class);
        DatabaseManager databaseManager = mock(DatabaseManager.class);

        User testUser = new User(
                "student",
                "1234",
                Role.STUDENT,
                "Max",
                "Mustermann");

        when(repository.getUserByUsername("student"))
                .thenReturn(testUser);

        UserService service =
                new UserService(repository, databaseManager);

        User ergebnis =
                service.login("student", "1234");

        assertNotNull(ergebnis);

        verify(repository).getUserByUsername("student");
    }
}