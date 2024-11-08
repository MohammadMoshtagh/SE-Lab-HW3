package ir.selab.tdd;

import ir.selab.tdd.domain.User;
import ir.selab.tdd.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserRepositoryTest {
    private UserRepository repository;

    @Before
    public void setUp() {
        List<User> userList = Arrays.asList(
                new User("admin", "1234"),
                new User("ali", "qwert"),
                new User("mohammad", "123asd"),
                new User("trump", "1234", "trump@gmail.com"));
        repository = new UserRepository(userList);
    }

    @Test
    public void getContainingUser__ShouldReturn() {
        User ali = repository.getUserByUsername("ali");
        assertNotNull(ali);
        assertEquals("ali", ali.getUsername());
        assertEquals("qwert", ali.getPassword());
    }

    @Test
    public void getNotContainingUser__ShouldReturnNull() {
        User user = repository.getUserByUsername("reza");
        assertNull(user);
    }

    @Test
    public void createRepositoryWithDuplicateUsers__ShouldThrowException() {
        User user1 = new User("ali", "1234");
        User user2 = new User("ali", "4567");
        assertThrows(IllegalArgumentException.class, () -> {
            new UserRepository(List.of(user1, user2));
        });
    }

    @Test
    public void addNewUser__ShouldIncreaseUserCount() {
        int oldUserCount = repository.getUserCount();

        // Given
        String username = "reza";
        String password = "123abc";
        String email = "reza@sharif.edu";
        User newUser = new User(username, password);

        // When
        repository.addUser(newUser);

        // Then
        assertEquals(oldUserCount + 1, repository.getUserCount());
    }

    @Test
    public void createUserWithDuplicateEmail__ShouldFail() {
        User user1 = new User("kamela", "loser", "trump@gmail.com");
        boolean isAdded = repository.addUser(user1);
        assertFalse(isAdded);
    }

    @Test
    public void removeExistingUser_ShouldRemoveFromInternalStorage() {
        String username = "ali";

        boolean removed = repository.removeUser(username);

        assertTrue(removed);
        assertNull(repository.getUserByUsername(username));
    }

    @Test
    public void removeNonExistingUser_ShouldNotAffectStorage() {
        String username = "nonexistent";
        int initialCount = repository.getUserCount();

        boolean removed = repository.removeUser(username);

        assertFalse(removed);
        assertEquals(initialCount, repository.getUserCount());
    }

    @Test
    public void removeExistingUser_ShouldDecreaseUserCount() {
        String username = "ali";
        int initialCount = repository.getUserCount();

        boolean removed = repository.removeUser(username);

        assertTrue(removed);
        assertEquals(initialCount - 1, repository.getUserCount());
        assertNull(repository.getUserByUsername(username));
    }

    @Test
    public void removeUser_ShouldOnlyRemoveSpecifiedUser() {
        String usernameToRemove = "admin";
        String otherUsername = "ali";
        int initialCount = repository.getUserCount();

        boolean removed = repository.removeUser(usernameToRemove);

        assertTrue(removed);
        assertEquals(initialCount - 1, repository.getUserCount());
        assertNull(repository.getUserByUsername(usernameToRemove));
        assertNotNull(repository.getUserByUsername(otherUsername));
    }

    @Test
    public void getAllUsers_ShouldReturnAllUniqueUsers() {
        List<User> users = repository.getAllUsers();

        assertNotNull(users);
        assertEquals(3, users.size());
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("admin")));
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("ali")));
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("mohammad")));
    }

}
