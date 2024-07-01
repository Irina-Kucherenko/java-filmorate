package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class JDBCUserStorageTest {

    private final UserService userService;

    @Test
    void createUserTest() {
        User createdUser = userService.createUser(getUser());
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isEqualTo(1);
        assertThat(createdUser.getName()).isEqualTo("Joe");
        assertThat(createdUser.getLogin()).isEqualTo("Hans");
        assertThat(createdUser.getEmail()).isEqualTo("hans345@gmail.com");
        assertThat(createdUser.getBirthday()).isEqualTo(LocalDate.now().minusYears(10));
    }

    @Test
    void updateUserTest() {
        User createdUser = userService.createUser(getUser());
        createdUser.setName("Adam");
        createdUser.setLogin("Gontier");
        createdUser.setEmail("threedaysgrace3@gmail.com");
        createdUser.setBirthday(LocalDate.of(1978, 5, 25));
        userService.updateUser(createdUser);
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isEqualTo(1);
        assertThat(createdUser.getName()).isEqualTo("Adam");
        assertThat(createdUser.getLogin()).isEqualTo("Gontier");
        assertThat(createdUser.getEmail()).isEqualTo("threedaysgrace3@gmail.com");
        assertThat(createdUser.getBirthday()).isEqualTo(LocalDate.of(1978, 5, 25));
    }

    @Test
    void getUsersTest() {
        userService.createUser(getUser());
        userService.createUser(getUser());

        List<User> userList = userService.getUsers();

        assertThat(userList).isNotNull().hasSize(2);
    }

    @Test
    void getUserTest() {
        userService.createUser(getUser());

        User result = userService.getUser(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void addFriendTest() {
        User user1 = userService.createUser(getUser());
        User user2 = userService.createUser(getUser());

        User result = userService.addFriend(user1.getId(), user2.getId());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2);
    }

    @Test
    void deleteFriendTest() {
        User user1 = userService.createUser(getUser());
        User user2 = userService.createUser(getUser());

        String result = userService.deleteFriend(user1.getId(), user2.getId());

        assertThat(result).isEqualTo("Друг успешно удалён!");
    }

    @Test
    void getCommonFriendsTest() {
        User user1 = userService.createUser(getUser());
        User user2 = userService.createUser(getUser());
        User user3 = userService.createUser(getUser());
        User user4 = userService.createUser(getUser());

        userService.addFriend(user1.getId(), user2.getId());
        userService.addFriend(user1.getId(), user3.getId());
        userService.addFriend(user4.getId(), user2.getId());
        userService.addFriend(user4.getId(), user3.getId());

        List<User> commonFriendsOfUser1WithUser4 = userService.getCommonFriendsList(user1.getId(), user4.getId());
        List<User> commonFriendsOfUser4WithUser1 = userService.getCommonFriendsList(user4.getId(), user1.getId());

        assertThat(commonFriendsOfUser1WithUser4).hasSameSizeAs(commonFriendsOfUser4WithUser1);
    }

    @Test
    void getFriendsTest() {
        User user1 = userService.createUser(getUser());
        User user2 = userService.createUser(getUser());
        User user3 = userService.createUser(getUser());

        userService.addFriend(user1.getId(), user2.getId());
        userService.addFriend(user1.getId(), user3.getId());

        List<User> friendsList = userService.getFriends(user1.getId());

        assertThat(friendsList).isNotNull().hasSize(2);
    }

    private User getUser() {
        User user = new User();
        user.setName("Joe");
        user.setLogin("Hans");
        user.setEmail("hans345@gmail.com");
        user.setBirthday(LocalDate.now().minusYears(10));
        return user;
    }
}
