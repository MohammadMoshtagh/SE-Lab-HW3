package ir.selab.tdd.service;

import ir.selab.tdd.domain.User;
import ir.selab.tdd.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public boolean loginWithUsername(String username, String password) {
        User userByUsername = repository.getUserByUsername(username);
        if (userByUsername == null) {
            return false;
        }
        return userByUsername.getPassword().equals(password);
    }

    public boolean loginWithEmail(String email, String password) {
        User userByEmail = repository.getUserByEmail(email);
        if (userByEmail == null) {
            return false;
        }
        return userByEmail.getPassword().equals(password);
    }

    public boolean registerUser(String username, String password) {
        User user = new User(username, password);
        return repository.addUser(user);
    }

    public boolean registerUser(String username, String password, String email) {
        User user = new User(username, password);
        user.setEmail(email);
        return repository.addUser(user);
    }

    public boolean removeUser(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        return repository.removeUser(username);
    }

    public List<User> getAllUsers() {
        // TODO: implement
        return null;
    }

    public boolean changeUserEmail(String username, String newEmail) {
        User user = repository.getUserByUsername(username);
        if (user == null || user.getEmail() == null) {
            return false;
        }
        user.setEmail(newEmail);
        return true;
    }
}
