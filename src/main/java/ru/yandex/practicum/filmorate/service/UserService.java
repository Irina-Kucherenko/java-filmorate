package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
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
        userStorage.checkUser(user.getId());
        return userStorage.updateUser(user);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User addFriend(Integer currentUserId, Integer newFriendId) {
        userStorage.checkUser(currentUserId);
        userStorage.checkUser(newFriendId);
        if (userStorage.addFriend(currentUserId, newFriendId)) {
            return userStorage.getUser(newFriendId);
        } else {
            throw new ValidationException("Ошибка при добавлении друга!");
        }
    }

    public String deleteFriend(Integer currentUserId, Integer deleteFriendId) {
        if (userStorage.deleteFriend(currentUserId, deleteFriendId)) {
            return "Друг успешно удалён!";
        } else {
            throw new ResourceNotFoundException("Друг не был найден!");
        }
    }

    public List<User> getCommonFriendsList(Integer currentUserId, Integer otherUserId) {
        userStorage.checkUser(currentUserId);
        userStorage.checkUser(otherUserId);
        return userStorage.getCommonFriendsList(currentUserId, otherUserId);
    }

    public List<User> getFriends(Integer currentUserId) {
        userStorage.checkUser(currentUserId);
        return userStorage.getFriends(currentUserId);
    }
}
