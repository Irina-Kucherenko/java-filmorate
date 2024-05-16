package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryStorageFilm implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private final Map<Integer, Set<Integer>> likesMap = new HashMap<>();
    private int idCounter = 1;

    @Override
    public Film addFilm(Film film) {
        film.setId(idCounter);
        films.put(film.getId(), film);
        idCounter += 1;
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new ResourceNotFoundException("Фильм не найден.");
        }
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public boolean addLike(Integer filmId, Integer userId) {
        if (likesMap.containsKey(filmId)) {
            return likesMap.get(filmId).add(userId);
        } else {
            Set<Integer> likes = new HashSet<>();
            likes.add(userId);
            likesMap.put(filmId, likes);
            return true;
        }
    }

    @Override
    public boolean deleteLike(Integer filmId, Integer userId) {
        if (likesMap.containsKey(filmId)) {
            return likesMap.get(filmId).remove(userId);
        } else {
            throw new ResourceNotFoundException("Фильм не найден.");
        }
    }

    @Override
    public List<Film> getTopFilms(int countFilms) {
       return likesMap.entrySet()
               .stream()
               .sorted((o1, o2) -> o2.getValue().size() - o1.getValue().size())
               .map(e -> films.get(e.getKey()))
               .limit(countFilms)
               .toList();
    }

    @Override
    public List<Film> getUserLikes(Integer userId) {
        return likesMap.entrySet()
                .stream()
                .filter(e -> e.getValue().contains(userId))
                .map(e -> films.get(e.getKey()))
                .toList();
    }


}
