package ir.selab.tdd.repository;

import ir.selab.tdd.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {
    private final Map<String, User> usersByUserName;
    private final Map<String, User> usersByEmail;

    public UserRepository(List<User> users) {
        this.usersByUserName = users.stream().collect(Collectors.toMap(User::getUsername, u -> u, (u1, u2) -> {
            throw new IllegalArgumentException("Two users can not have the same username!");
        }));

        this.usersByEmail = users.stream().filter(user -> user.getEmail() != null)
                .collect(Collectors.toMap(User::getEmail, u -> u, (u1, u2) -> {
                    throw new IllegalArgumentException("Two users can not have the same email!");
                }));
    }

    public List<User> getAllUsers() {
        Map<String, User> combinedUsers = new HashMap<>(usersByUserName);

        for (Map.Entry<String, User> entry : usersByEmail.entrySet()) {
            combinedUsers.putIfAbsent(entry.getKey(), entry.getValue());
        }

        return new ArrayList<>(combinedUsers.values());
    }

    public User getUserByUsername(String username) {
        return usersByUserName.get(username);
    }

    public User getUserByEmail(String email) {
        return usersByEmail.get(email);
    }

    public boolean addUser(User user) {
        if (usersByUserName.containsKey(user.getUsername())) {
            return false;
        }
        if (user.getEmail() != null && usersByEmail.containsKey(user.getEmail())) {
            return false;
        }
        usersByUserName.put(user.getUsername(), user);
        usersByEmail.put(user.getEmail(), user);
        return true;
    }

    public boolean removeUser(String username) {
        if (!usersByUserName.containsKey(username)) {
            return false;
        }
        usersByUserName.remove(username);
        return true;
    }

    public int getUserCount() {
        return usersByUserName.size();
    }
}
