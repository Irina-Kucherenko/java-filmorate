package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryStorageUser implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Set<Integer>> friendsMap = new HashMap<>();
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

    @Override
    public boolean addFriend(Integer currentUserId, Integer newFriendId) {
        return updateFriendsList(currentUserId, newFriendId) && updateFriendsList(newFriendId, currentUserId);
    }

    @Override
    public boolean deleteFriend(Integer currentUserId, Integer deleteFriendId) {
        return deleteFriendFromList(currentUserId, deleteFriendId) &&
                deleteFriendFromList(deleteFriendId, currentUserId);
    }

    @Override
    public List<User> getCommonFriendsList(Integer currentUserId, Integer otherUserId) {
        Set<Integer> currentUserFriends = friendsMap.getOrDefault(currentUserId, new HashSet<>());
        Set<Integer> friendsOfFriend = friendsMap.getOrDefault(otherUserId, new HashSet<>());
        currentUserFriends.retainAll(friendsOfFriend);
        return currentUserFriends.stream().map(users::get).toList();
    }

    @Override
    public List<User> getFriends(Integer currentUserId) {
        return friendsMap.get(currentUserId).stream().map(users::get).toList();
    }

    private boolean updateFriendsList(Integer currentUserId, Integer newFriendId) {
        if (friendsMap.containsKey(currentUserId)) {
            return friendsMap.get(currentUserId).add(newFriendId);
        } else {
            Set<Integer> friends = new HashSet<>();
            friends.add(newFriendId);
            friendsMap.put(currentUserId, friends);
            return true;
        }
    }

    private boolean deleteFriendFromList(Integer currentUserId, Integer newFriendId) {
        if (friendsMap.containsKey(currentUserId)) {
            return friendsMap.get(currentUserId).remove(newFriendId);
        } else {
            throw new ResourceNotFoundException("Пользователь не найден.");
        }
    }

    public void checkUser(Integer userId) {
        if (!users.containsKey(userId)) {
            throw new ResourceNotFoundException("Пользователь с id" + userId + " не найден.");
        }
    }

}
