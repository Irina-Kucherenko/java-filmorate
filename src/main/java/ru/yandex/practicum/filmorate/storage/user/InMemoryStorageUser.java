package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryStorageUser implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 1;

    @Override
    public User createUser(User user) {
        user.setId(idCounter);
        users.put(user.getId(), user);
        idCounter += 1;
        return user;
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}
