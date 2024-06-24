package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MPAController {

    private final MpaService mpaService;

    @GetMapping(value = "/mpa/{id}", produces = APPLICATION_JSON_VALUE)
    public MPA getMpa(@PathVariable(name = "id") Integer id) {
        log.info("Получение MPA с id" + id + ": ");
        return mpaService.getMpa(id);
    }

    @GetMapping(value = "/mpa", produces = APPLICATION_JSON_VALUE)
    public List<MPA> getMpaList() {
        log.info("Получение списка MPA: ");
        return mpaService.getMpaList();
    }
}
