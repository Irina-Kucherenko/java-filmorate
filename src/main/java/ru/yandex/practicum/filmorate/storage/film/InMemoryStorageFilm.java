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
    public void addFilm(Film film) {
        film.setId(idCounter);
        films.put(film.getId(), film);
        idCounter += 1;
    }

    @Override
    public void updateFilm(Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }
}
