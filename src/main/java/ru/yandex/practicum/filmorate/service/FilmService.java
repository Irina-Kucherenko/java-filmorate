package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public String addLike(Integer filmId, Integer userId) {
        if (filmStorage.addLike(filmId, userId)) {
            return "Лайк успешно добавлен!";
        } else {
            return "Лайк был добавлен ранее!";
        }
    }

    public String deleteLike(Integer filmId, Integer userId) {
        if (filmStorage.deleteLike(filmId, userId)) {
            return "Лайк успешно удалён!";
        } else {
            return "Лайк был удалён ранее!";
        }
    }

    public List<Film> getTopFilms(Integer countFilms) {
        if (countFilms == null) {
            countFilms = 10;
        }
        return filmStorage.getTopFilms(countFilms);
    }

    public List<Film> getUserLikes(Integer userId) {
        return filmStorage.getUserLikes(userId);
    }
}
