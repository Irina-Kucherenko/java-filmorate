package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validator.ReleaseDate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film {

    private Integer id;

    @NotBlank(message = "Название фильма не должно быть пустым!")
    private String name;

    @Size(max = 200, message = "Превышено количество символов!")
    private String description;

    @ReleaseDate(message = "Некорректная дата!")
    private LocalDate releaseDate;

    @Min(value = 1, message = "Длительность должна быть больше 0!")
    private int duration;

    @NotNull
    private MPA mpa;

    @NotNull
    private List<Genre> genres = Collections.emptyList();
}