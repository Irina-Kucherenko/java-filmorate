package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService serviceUser;

    @PostMapping(value = "/users", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public User createUser(@Valid @RequestBody User user) {
        log.info("Создание нового пользователя: " + user);
        return serviceUser.createUser(user);
    }

    @PutMapping(value = "/users", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Обновление пользователя: " + user.getId());
        return serviceUser.updateUser(user);
    }

    @GetMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        log.info("Весь список пользователей:");
        return serviceUser.getUsers();
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}", produces = APPLICATION_JSON_VALUE)
    public String deleteFriend(@PathVariable(name = "id") Integer id,
                               @PathVariable(name = "friendId") Integer friendId) {
        log.info("Удаление друга с id" + friendId + " у пользователя с id" + id + ":");
        return serviceUser.deleteFriend(id, friendId);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}", produces = APPLICATION_JSON_VALUE)
    public String addFriend(@PathVariable(name = "id") Integer id,
                            @PathVariable(name = "friendId") Integer friendId) {
        log.info("Добавление нового друга с id" + friendId + " к пользователю с id" + id + ":");
        return serviceUser.addFriend(id, friendId);
    }

    @GetMapping(value = "/users/{id}/friends/common/{otherId}", produces = APPLICATION_JSON_VALUE)
    public List<User> getCommonFriends(@PathVariable(name = "id") Integer id,
                                       @PathVariable(name = "otherId") Integer otherId) {
        log.info("Весь список общих друзей пользователей с id" + id + " и id" + otherId + ":");
        return serviceUser.getCommonFriendsList(id, otherId);
    }

    @GetMapping(value = "/users/{id}/friends", produces = APPLICATION_JSON_VALUE)
    public List<User> getFriends(@PathVariable(name = "id") Integer id) {
        log.info("Весь список друзей пользователя с id" + id + ":");
        return serviceUser.getFriends(id);
    }

}