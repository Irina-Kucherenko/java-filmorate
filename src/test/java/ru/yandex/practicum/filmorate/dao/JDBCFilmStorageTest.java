package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class JDBCFilmStorageTest {

    private final FilmService filmService;
    private final UserService userService;

    @Test
     void addFilmTest() {
        Film addedFilm = filmService.addFilm(getFilm());
        assertThat(addedFilm).isNotNull();
        assertThat(addedFilm.getId()).isEqualTo(1);
        assertThat(addedFilm.getName()).isEqualTo("Test Film");
        assertThat(addedFilm.getDescription()).isEqualTo("Test Description");
        assertThat(addedFilm.getReleaseDate()).isEqualTo(LocalDate.now());
        assertThat(addedFilm.getDuration()).isEqualTo(120);
        assertThat(addedFilm.getMpa().getId()).isEqualTo(1);
        assertThat(addedFilm.getGenres()).hasSize(1);
    }

    @Test
    void updateFilmTest() {
        Film addedFilm = filmService.addFilm(getFilm());
        addedFilm.setName("Rage");
        addedFilm.setDescription("Good");
        addedFilm.setReleaseDate(LocalDate.now().plusDays(2));
        addedFilm.setDuration(150);
        addedFilm.setMpa(getMpa(2));
        addedFilm.setGenres(getListGenre(4, 1, 3));
        addedFilm = filmService.updateFilm(addedFilm);
        assertThat(addedFilm).isNotNull();
        assertThat(addedFilm.getId()).isEqualTo(1);
        assertThat(addedFilm.getName()).isEqualTo("Rage");
        assertThat(addedFilm.getDescription()).isEqualTo("Good");
        assertThat(addedFilm.getReleaseDate()).isEqualTo(LocalDate.now().plusDays(2));
        assertThat(addedFilm.getDuration()).isEqualTo(150);
        assertThat(addedFilm.getMpa().getId()).isEqualTo(2);
        assertThat(addedFilm.getGenres()).hasSize(3);
    }

    @Test
    void getFilmsTest() {
        filmService.addFilm(getFilm());
        filmService.addFilm(getFilm());
        List<Film> films = filmService.getFilms();

        assertThat(films).isNotNull().hasSize(2);
    }

    @Test
    void getFilmTest() {
        filmService.addFilm(getFilm());
        Film result = filmService.getFilm(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void addLikeTest() {
        Film addedFilm = filmService.addFilm(getFilm());
        User user = userService.createUser(getUser());
        Film result = filmService.addLike(addedFilm.getId(), user.getId());

        assertThat(result.getId()).isEqualTo(addedFilm.getId());
    }

    @Test
    void deleteLikeTest() {
        Film addedFilm = filmService.addFilm(getFilm());
        User user = userService.createUser(getUser());
        filmService.addLike(addedFilm.getId(), user.getId());
        String result = filmService.deleteLike(addedFilm.getId(), user.getId());

        assertThat(result).isEqualTo("Лайк успешно удалён!");
    }

    @Test
    void getTopFilmsTest() {
        Film film1 = filmService.addFilm(getFilm());
        Film film2 = filmService.addFilm(getFilm());

        User user1 = userService.createUser(getUser());
        User user2 = userService.createUser(getUser());

        filmService.addLike(film1.getId(), user1.getId());
        filmService.addLike(film2.getId(), user1.getId());
        filmService.addLike(film1.getId(), user2.getId());

        List<Film> result = filmService.getTopFilms(0);

        assertThat(result).isNotNull().hasSize(2);
    }

    @Test
    void getUserLikesTest() {
        Film film1 = filmService.addFilm(getFilm());
        Film film2 = filmService.addFilm(getFilm());

        User user1 = userService.createUser(getUser());

        filmService.addLike(film1.getId(), user1.getId());
        filmService.addLike(film2.getId(), user1.getId());

        List<Film> userLikesFilmsList = filmService.getUserLikes(user1.getId());

        assertThat(userLikesFilmsList).isNotNull().hasSize(2);
    }


    private Film getFilm() {
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(120);
        film.setMpa(getMpa(1));
        film.setGenres(getListGenre(3));
        return film;
    }

    private MPA getMpa(Integer id) {
        MPA mpa = new MPA();
        mpa.setId(id);
        return mpa;
    }

    private List<Genre> getListGenre(Integer... ids) {
        List<Genre> listGenres = new ArrayList<>();
        for (Integer id : ids) {
            Genre genre = new Genre();
            genre.setId(id);
            listGenres.add(genre);
        }
        return listGenres;
    }

    private User getUser() {
        User user = new User();
        user.setName("Joe");
        user.setLogin("Hans");
        user.setEmail("hans345@gmail.com");
        user.setBirthday(LocalDate.now().minusYears(10));
        return user;
    }
}
