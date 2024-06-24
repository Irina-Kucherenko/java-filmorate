package ru.yandex.practicum.filmorate.model;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class FilmModelTest {

    @Test
    void createValidFilmTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        MPA mpa = new MPA();
        mpa.setId(1);
        mpa.setName("G");
        List<Genre> genres = new ArrayList<>();
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Комедия");
        genres.add(genre);
        Film film = Film.builder()
                .id(1)
                .name("Ravage")
                .description("Good film")
                .releaseDate(LocalDate.now())
                .duration(10)
                .mpa(mpa)
                .genres(genres).build();
        Assertions.assertThat(film).isNotNull();
        var violations = validator.validate(film);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void createInvalidFilmTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        List<Genre> genres = new ArrayList<>();
        MPA mpa = new MPA();
        Film film = Film.builder()
                .id(1)
                .name("")
                .description("Good filmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm")
                .releaseDate(LocalDate.MIN)
                .duration(0).build();
        var violations = validator.validate(film);
        Assertions.assertThat(violations).hasSize(6);
    }
}
