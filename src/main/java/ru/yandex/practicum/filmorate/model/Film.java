package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.ReleaseDate;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class Film {

    private int id;

    @NotBlank(message = "Название фильма не должно быть пустым!")
    private String name;

    @Size(max = 200, message = "Превышено количество символов!")
    private String description;

    @ReleaseDate(message = "Некорректная дата!")
    private LocalDate releaseDate;

    @Min(value = 1, message = "Длительность должна быть больше 0!")
    private int duration;
}