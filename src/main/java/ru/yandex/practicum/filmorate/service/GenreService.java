package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreStorage genreStorage;

    public Genre getGenre(Integer id) {
        return genreStorage.getGenre(id);
    }

    public List<Genre> getGenreList() {
        return genreStorage.getGenreList();
    }
}
