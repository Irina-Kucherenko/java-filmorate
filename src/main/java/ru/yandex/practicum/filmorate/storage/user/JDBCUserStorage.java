package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Primary
public class JDBCUserStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    private final UserMapper userMapper;

    private static final int FRIEND_STATUS_SEND_REQUEST_ID = 1;

    private static final int FRIEND_STATUS_ACCEPT_REQUEST_ID = 2;

    @Override
    public User createUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingColumns("email", "login", "user_name", "birthday")
                .usingGeneratedKeyColumns("user_id");
        int userId = simpleJdbcInsert.executeAndReturnKey(userMapper.userToMap(user)).intValue();
        return getUser(userId);
    }

    @Override
    public User updateUser(User user) {
        jdbcTemplate.update("UPDATE USERS " +
                        "SET email = ?, login = ?, user_name = ?, birthday = ? " +
                        "WHERE user_id = ?",
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return getUser(user.getId());
    }

    @Override
    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        jdbcTemplate.query("SELECT * FROM USERS", rs -> {
            do {
                User user = userMapper.mapRow(rs, rs.getRow());
                if (user != null) {
                    userList.add(user);
                }
            } while (rs.next());
        });
        return userList;
    }

    @Override
    public User getUser(Integer id) {
        return jdbcTemplate.query("SELECT * FROM USERS WHERE user_id = " + id, rs -> {
            if (!rs.next()) {
                throw new ResourceNotFoundException("Пользователь не найден");
            }
            User user = userMapper.mapRow(rs, rs.getRow());
            if (user != null) {
                return user;
            } else {
                throw new ResourceNotFoundException("Пользователь не найден");
            }
        });
    }

    @Override
    public boolean addFriend(Integer currentUserId, Integer newFriendId) {
        try {
            String query = String.format("INSERT INTO USERS_IN_TOUCH (requester_id, receiver_id, friends_status_id) " +
                            "VALUES (%d, %d, %d)",
                    currentUserId, newFriendId, FRIEND_STATUS_ACCEPT_REQUEST_ID);
            jdbcTemplate.execute(query);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean deleteFriend(Integer currentUserId, Integer deleteFriendId) {
        try {
            String query = String.format("DELETE FROM USERS_IN_TOUCH " +
                            "WHERE requester_id = %d AND receiver_id = %d OR receiver_id = %d AND requester_id = %d",
                    currentUserId, deleteFriendId, deleteFriendId, currentUserId);
            jdbcTemplate.execute(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<User> getCommonFriendsList(Integer currentUserId, Integer otherUserId) {
        List<User> commonFriendsList = new ArrayList<>();
        String query = String.format("SELECT receiver_id FROM USERS_IN_TOUCH " +
                "WHERE requester_id = %d AND receiver_id IN (" +
                "SELECT receiver_id FROM USERS_IN_TOUCH WHERE requester_id = %d )", currentUserId, otherUserId);
        jdbcTemplate.query(query, rs -> {
            do {
                User user = getUser(rs.getInt("receiver_id"));
                if (user != null) {
                    commonFriendsList.add(user);
                }
            } while (rs.next());
        });
        return commonFriendsList;
    }

    @Override
    public List<User> getFriends(Integer currentUserId) {
        List<User> friendsList = new ArrayList<>();
        String query = String.format("SELECT receiver_id FROM USERS_IN_TOUCH " +
                "WHERE requester_id = %d", currentUserId);
        jdbcTemplate.query(query, rs -> {
            do {
                User user = getUser(rs.getInt("receiver_id"));
                if (user != null) {
                    friendsList.add(user);
                }
            } while (rs.next());
        });
        System.out.println(query + " " + friendsList.size());
        return friendsList;
    }

    @Override
    public void checkUser(Integer userId) {
        List<User> userList = new ArrayList<>();
        jdbcTemplate.query("SELECT * FROM USERS WHERE user_id = " + userId, rs -> {
            userList.add(userMapper.mapRow(rs, rs.getRow()));
        });
        if (userList.isEmpty()) {
            throw new ResourceNotFoundException("Пользователь не найден");
        }
        System.out.println("Пользователь: " + userId);
    }
}
