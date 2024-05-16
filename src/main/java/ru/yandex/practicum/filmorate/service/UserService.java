package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;


    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public String addFriend(Integer currentUserId, Integer newFriendId) {
        if (userStorage.addFriend(currentUserId, newFriendId)) {
            return "Друг успешно добавлен!";
        } else {
            return "Друг был добавлен ранее!";
        }
    }

    public String deleteFriend(Integer currentUserId, Integer deleteFriendId) {
        if (userStorage.deleteFriend(currentUserId, deleteFriendId)) {
            return "Друг успешно удалён!";
        } else {
            return "Друг был удалён ранее!";
        }
    }

    public List<User> getCommonFriendsList(Integer currentUserId, Integer otherUserId) {
        return userStorage.getCommonFriendsList(currentUserId, otherUserId);
    }

    public List<User> getFriends(Integer currentUserId) {
        return userStorage.getFriends(currentUserId);
    }
}
