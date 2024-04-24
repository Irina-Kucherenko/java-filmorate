package ru.yandex.practicum.filmorate.validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value=ElementType.FIELD)
@Retention(value=RetentionPolicy.RUNTIME)
@Constraint(validatedBy=ReleaseDateValidator.class)
public @interface ReleaseDate {

    String message() default "{ReleaseDate}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
