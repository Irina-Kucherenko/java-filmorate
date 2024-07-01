package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmService serviceFilm;

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

    @GetMapping(value = "/films/{id}", produces = APPLICATION_JSON_VALUE)
    public Film getFilm(@PathVariable(name = "id") Integer id) {
        log.info("Получения фильма с id" + id + ": ");
        return serviceFilm.getFilm(id);
    }

    @GetMapping(value = "/films", produces = APPLICATION_JSON_VALUE)
    public List<Film> getFilms() {
        log.info("Весь каталог фильмов:");
        return serviceFilm.getFilms();
    }

    @PutMapping(value = "/films/{id}/like/{userId}", produces = APPLICATION_JSON_VALUE)
    public Film addLike(@PathVariable(name = "id") Integer id,
                        @PathVariable(name = "userId") Integer userId) {
        log.info("Добавление лайка пользователя с id" + userId + " к фильму с id" + id + ":");
        return serviceFilm.addLike(id, userId);
    }

    @DeleteMapping(value = "/films/{id}/like/{userId}", produces = APPLICATION_JSON_VALUE)
    public String deleteLike(@PathVariable(name = "id") Integer id,
                             @PathVariable(name = "userId") Integer userId) {
        log.info("Удаление лайка пользователя с id" + userId + " у фильма с id" + id + ":");
        return serviceFilm.deleteLike(id, userId);
    }

    @GetMapping(value = "/films/popular", produces = APPLICATION_JSON_VALUE)
    public List<Film> getTopFilms(@RequestParam(name = "count") Integer count) {
        log.info("Список лучших фильмов по мнению пользователей:");
        return serviceFilm.getTopFilms(count);
    }

    @GetMapping(value = "/films/like/{userId}", produces = APPLICATION_JSON_VALUE)
    public List<Film> getUserLikes(@PathVariable(name = "userId") Integer userId) {
        log.info("Список понравившихся фильмов пользователя с id" + userId + ":");
        return serviceFilm.getUserLikes(userId);
    }
}
