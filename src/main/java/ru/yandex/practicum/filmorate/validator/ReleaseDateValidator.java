package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import java.time.LocalDate;


public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {

    private final LocalDate CUTOFF = LocalDate.parse("1895-12-28");


    @Override
    public void initialize(ReleaseDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        return CUTOFF.isBefore(date);
    }
}