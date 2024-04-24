package ru.yandex.practicum.filmorate.model;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;

class FilmModelTest {

    @Test
    void createValidFilmTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Film film = Film.builder()
                .id(1)
                .name("Ravage")
                .description("Good film")
                .releaseDate(LocalDate.now())
                .duration(10).build();
        Assertions.assertThat(film).isNotNull();
        var violations = validator.validate(film);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void createInvalidFilmTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Film film = Film.builder()
                .id(1)
                .name("")
                .description("Good filmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm")
                .releaseDate(LocalDate.MIN)
                .duration(0).build();
        var violations = validator.validate(film);
        Assertions.assertThat(violations).hasSize(4);
    }
}
