package ir.selab.tdd;

import ir.selab.tdd.repository.UserRepository;
import ir.selab.tdd.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService userService;

    @Before
    public void setUp() {
        UserRepository userRepository = new UserRepository(List.of());
        userService = new UserService(userRepository);
        userService.registerUser("admin", "1234");
        userService.registerUser("ali", "qwert");
        userService.registerUser("hasti", "123456", "hasti@gmail.com");
        userService.registerUser("asghar", "rand", "asghar@gmail.com");
    }

    @Test
    public void createNewValidUser__ShouldSuccess() {
        String username = "reza";
        String password = "123abc";
        boolean b = userService.registerUser(username, password);
        assertTrue(b);
    }

    @Test
    public void createNewValidUserWithEmail__ShouldSuccess() {
        String username = "rodjer";
        String password = "123";
        String email = "ro@gmail.com";
        boolean b = userService.registerUser(username, password, email);
        assertTrue(b);
    }

    @Test
    public void createNewDuplicateUser__ShouldFail() {
        String username = "ali";
        String password = "123abc";
        boolean b = userService.registerUser(username, password);
        assertFalse(b);
    }

    @Test
    public void loginWithValidUsernameAndPassword__ShouldSuccess() {
        boolean login = userService.loginWithUsername("admin", "1234");
        assertTrue(login);
    }

    @Test
    public void loginWithValidUsernameAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithUsername("admin", "abcd");
        assertFalse(login);
    }

    @Test
    public void loginWithInvalidUsernameAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithUsername("ahmad", "abcd");
        assertFalse(login);
    }

    @Test
    public void loginWithValidEmailAndPassword__ShouldSuccess() {
        boolean login = userService.loginWithEmail("hasti@gmail.com", "123456");
        assertTrue(login);
    }

    @Test
    public void loginWithValidEmailAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithEmail("hasti@gmail.com", "abcd");
        assertFalse(login);
    }

    @Test
    public void loginWithInvalidEmailAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithEmail("hasti@g.c", "123456");
        assertFalse(login);
    }

    @Test
    public void changeUserEmailWithValidUsername__ShouldSuccess() {
        boolean changed = userService.changeUserEmail("asghar", "asgharagha@gmail.com");
        assertTrue(changed);
    }

    @Test
    public void changeUserEmailWithInvalidEmail__ShouldFail() {
        boolean changed = userService.changeUserEmail("mashti@gmail.com", "mashti1@gmail.com");
        assertFalse(changed);
    }
}
