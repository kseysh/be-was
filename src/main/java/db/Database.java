package db;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import model.User;

import java.util.Collection;
import java.util.Map;

public class Database {

    private static final Map<String, User> users = new ConcurrentHashMap<>();

    private Database() {
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserByUserId(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
