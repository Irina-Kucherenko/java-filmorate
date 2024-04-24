package ru.yandex.practicum.filmorate.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class UserModelTest {

    @Test
    void createValidUserTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        User user = User.builder()
                .id(1)
                .email("irinaspbirina@gmail.com")
                .login("redemption")
                .nick("боба")
                .birthday(LocalDate.parse("2005-04-28")).build();
        Assertions.assertThat(user).isNotNull();
        var violations = validator.validate(user);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void createInvalidUserTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        User user = User.builder()
                .id(1)
                .email("irinaspbirinagmail.com")
                .login("")
                .nick("")
                .birthday((LocalDate.MAX)).build();
        Assertions.assertThat(user).isNotNull();
        var violations = validator.validate(user);
        Assertions.assertThat(violations).hasSize(3);
    }

    @Test
    void equalsLoginAndNickTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        User user = User.builder()
                .id(1)
                .email("irinaspbirina@gmail.com")
                .login("redemption")
                .nick("")
                .birthday(LocalDate.parse("2005-04-28")).build();
        Assertions.assertThat(user.getNick()).isEqualTo(user.getLogin());
        var violations = validator.validate(user);
        Assertions.assertThat(violations).isEmpty();
    }
}
