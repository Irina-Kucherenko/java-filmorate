package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private static final int TOP_FILMS_NUMBER = 10;

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        filmStorage.checkFilm(film.getId());
        return filmStorage.updateFilm(film);
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film addLike(Integer filmId, Integer userId) {
        filmStorage.checkFilm(filmId);
        userStorage.checkUser(userId);
        if (filmStorage.addLike(filmId, userId)) {
            return filmStorage.getFilm(filmId);
        } else {
            throw new ValidationException("Ошибка при добавление лайка!");
        }
    }

    public String deleteLike(Integer filmId, Integer userId) {
        filmStorage.checkFilm(filmId);
        userStorage.checkUser(userId);
        if (filmStorage.deleteLike(filmId, userId)) {
            return "Лайк успешно удалён!";
        } else {
            return "Лайк был удалён ранее!";
        }
    }

    public List<Film> getTopFilms(Integer countFilms) {
        if (countFilms == null) {
            countFilms = TOP_FILMS_NUMBER;
        }
        return filmStorage.getTopFilms(countFilms);
    }

    public List<Film> getUserLikes(Integer userId) {
        userStorage.checkUser(userId);
        return filmStorage.getUserLikes(userId);
    }
}
