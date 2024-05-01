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

    @PostMapping(value = "/user", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public String createUser(@Valid @RequestBody User user) {
        log.info("Создание нового пользователя: " + user);
        try {
            serviceUser.createUser(user);
            return "Пользователь успешно создан";
        } catch (Exception e) {
            return "При создании нового пользователя произошла ошибка: " + e.getMessage();
        }
    }

    @PutMapping(value = "/user", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public String updateUser(@Valid @RequestBody User user) {
        log.info("Обновление пользователя: " + user.getId());
        try {
            serviceUser.updateUser(user);
            return "Пользователь успешно обновлён";
        } catch (Exception e) {
            return "При обновлении пользователя произошла ошибка: " + e.getMessage();
        }
    }

    @GetMapping(value = "/user", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        log.info("Весь список пользователей:");
        return serviceUser.getUsers();
    }
}