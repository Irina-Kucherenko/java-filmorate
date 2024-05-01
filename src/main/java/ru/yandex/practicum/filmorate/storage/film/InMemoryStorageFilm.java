package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryStorageFilm implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int idCounter = 0;

    @Override
    public Film addFilm(Film film) {
        film.setId(idCounter);
        films.put(film.getId(), film);
        idCounter += 1;
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }
}
