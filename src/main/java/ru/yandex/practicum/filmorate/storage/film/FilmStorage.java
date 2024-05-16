package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getFilms();

    boolean addLike(Integer filmId, Integer userId);

    boolean deleteLike(Integer filmId, Integer userId);

    List<Film> getTopFilms(int countFilms);

    List<Film> getUserLikes(Integer userId);


}
