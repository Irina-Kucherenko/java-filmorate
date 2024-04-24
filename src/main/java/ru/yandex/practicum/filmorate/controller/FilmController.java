package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class FilmController {

    @PostMapping(value = "/film", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public String addFilm(@Valid @RequestBody Film film) {
        log.info("Добавление нового фильма: " + film);
        try {
            return "Фильм успешно добавлен";
        } catch (Exception e) {
            return "При добавлении фильма произошла ошибка: " + e.getMessage();
        }
    }

    @PutMapping(value = "/film", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public String updateFilm(@Valid @RequestBody Film film) {
        log.info("Обновление фильма: " + film.getId());
        try {
            return "Фильм успешно обновлён";
        } catch (Exception e) {
            return "При обновлении фильма произошла ошибка: " + e.getMessage();
        }
    }

    @GetMapping(value = "/film", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<Film> getFilms() {
        log.info("Весь каталог фильмов:");
        return new ArrayList<>();
    }
}
