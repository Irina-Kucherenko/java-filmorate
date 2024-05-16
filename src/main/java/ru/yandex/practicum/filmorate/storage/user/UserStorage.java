package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User createUser(User user);

    User updateUser(User user);

    List<User> getUsers();

    User getUser(Integer id);

    boolean addFriend(Integer currentUserId, Integer newFriendId);

    boolean deleteFriend(Integer currentUserId, Integer deleteFriendId);

    List<User> getCommonFriendsList(Integer currentUserId, Integer otherUserId);

    List<User> getFriends(Integer currentUserId);

    void checkUser(Integer userId);

}
