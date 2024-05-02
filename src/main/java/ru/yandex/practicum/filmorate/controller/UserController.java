package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryStorageUser;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class UserController {

    private final InMemoryStorageUser serviceUser = new InMemoryStorageUser();

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
}