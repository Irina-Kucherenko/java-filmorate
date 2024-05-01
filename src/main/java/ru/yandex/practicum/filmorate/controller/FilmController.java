package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryStorageFilm;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class FilmController {

    private final InMemoryStorageFilm serviceFilm = new InMemoryStorageFilm();

    @PostMapping(value = "/films", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Добавление нового фильма: " + film);
        return serviceFilm.addFilm(film);
    }

    @PutMapping(value = "/films", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Обновление фильма: " + film.getId());
        return serviceFilm.updateFilm(film);
    }

    @GetMapping(value = "/films", produces = APPLICATION_JSON_VALUE)
    public List<Film> getFilms() {
        log.info("Весь каталог фильмов:");
        return serviceFilm.getFilms();
    }
}
