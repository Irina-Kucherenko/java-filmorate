package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping(value = "/genres/{id}", produces = APPLICATION_JSON_VALUE)
    public Genre getGenre(@PathVariable(name = "id") Integer id) {
        log.info("Получение жанра с id" + id + ": ");
        return genreService.getGenre(id);
    }

    @GetMapping(value = "/genres", produces = APPLICATION_JSON_VALUE)
    public List<Genre> getGenreList() {
        log.info("Получение списка жанров: ");
        return genreService.getGenreList();
    }
}
