package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getFilms();

    Film getFilm(Integer filmId);

    boolean addLike(Integer filmId, Integer userId);

    boolean deleteLike(Integer filmId, Integer userId);

    List<Film> getTopFilms(int countFilms);

    List<Film> getUserLikes(Integer userId);

    void checkFilm(Integer filmId);
}
