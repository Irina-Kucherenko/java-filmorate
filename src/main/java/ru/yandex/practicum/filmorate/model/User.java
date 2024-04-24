package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {

    private int id;

    @Email(regexp = "^\\S+@\\S+$", message = "Некорректный email!")
    private String email;

    @Pattern(regexp = "^\\S+$", message = "Логин пустой или имеет пробелы!")
    private String login;

    private String nick;

    @PastOrPresent(message = "Некорректная дата рождения!")

    private LocalDate birthday;

    public User(int id, String email, String login, String nick, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.nick = nick.isEmpty() ? login : nick;
        this.birthday = birthday;
    }
}