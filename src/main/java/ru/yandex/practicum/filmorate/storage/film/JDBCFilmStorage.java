package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Primary
@Slf4j
public class JDBCFilmStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    private final FilmMapper filmMapper;

    private final GenreStorage genreStorage;

    private final MpaStorage mpaStorage;


    @Override
    public Film addFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILMS")
                .usingColumns("title", "description", "release_date", "duration", "mpa_id")
                .usingGeneratedKeyColumns("film_id");
        try {
            int filmId = simpleJdbcInsert.executeAndReturnKey(filmMapper.filmToMap(film)).intValue();
            setFilmGenres(film.getGenres(), filmId);
            return getFilm(filmId);
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }

    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update("UPDATE FILMS " +
                        "SET title = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
                        "WHERE film_id = ?", film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        jdbcTemplate.update("DELETE FROM FILM_GENRES WHERE film_id = ?", film.getId());
        setFilmGenres(film.getGenres(), film.getId());
        return getFilm(film.getId());
    }

    @Override
    public List<Film> getFilms() {
        List<Film> filmList = new ArrayList<>();
        jdbcTemplate.query("SELECT * FROM FILMS", rs -> {

            do {
                Film film = filmMapper.mapRow(rs, rs.getRow());
                if (film != null) {
                    film.setMpa(mpaStorage.getMpa(rs.getInt("mpa_id")));
                    film.setGenres(getFilmGenres(film.getId()));
                    filmList.add(film);
                }
            } while (rs.next());
        });
        return filmList;

    }

    @Override
    public Film getFilm(Integer filmId) {
        return jdbcTemplate.query("SELECT * FROM FILMS WHERE film_id = " + filmId, rs -> {
            if (!rs.next()) {
                throw new ResourceNotFoundException("Фильм не найден");
            }
            Film film = filmMapper.mapRow(rs, rs.getRow());
            if (film != null) {
                film.setMpa(mpaStorage.getMpa(rs.getInt("mpa_id")));
                film.setGenres(getFilmGenres(film.getId()));
                return film;
            } else {
                throw new ResourceNotFoundException("Фильм не найден");
            }
        });
    }

    @Override
    public boolean addLike(Integer filmId, Integer userId) {
        try {
            jdbcTemplate.execute(String.format("MERGE INTO USER_FILM_LIKE (user_id, film_id) " +
                    "VALUES (%d, %d)", userId, filmId));
            return true;
        } catch (Exception e) {
            log.error("Ошибка при добавлении лайка");
            return false;
        }
    }

    @Override
    public boolean deleteLike(Integer filmId, Integer userId) {
        try {
            jdbcTemplate.execute(String.format("DELETE USER_FILM_LIKE WHERE user_id = %d AND film_id = %d ",
                    userId, filmId));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Film> getTopFilms(int countFilms) {
        List<Film> filmList = new ArrayList<>();
        String query = "SELECT film_id, COUNT (user_id) AS likes FROM USER_FILM_LIKE GROUP BY film_id " +
                "ORDER BY likes DESC LIMIT 10 ";
        jdbcTemplate.query(query, rs -> {
            do {
                var filmId = rs.getInt("film_id");
                filmList.add(getFilm(filmId));
            } while (rs.next());
        });
        System.out.println(filmList);
        return filmList;
    }

    @Override
    public List<Film> getUserLikes(Integer userId) {
        List<Film> filmList = new ArrayList<>();
        String query = "SELECT film_id FROM USER_FILM_LIKE WHERE user_id = " + userId;
        jdbcTemplate.query(query, rs -> {
            do {
                var filmId = rs.getInt("film_id");
                filmList.add(getFilm(filmId));
            } while (rs.next());
        });
        return filmList;
    }

    @Override
    public void checkFilm(Integer filmId) {
        jdbcTemplate.query("SELECT * FROM FILMS WHERE film_id = " + filmId, rs -> {
            if (rs.isBeforeFirst()) {
                throw new ResourceNotFoundException("Фильм с id" + filmId + " не найден.");
            }
        });
    }

    private void setFilmGenres(List<Genre> genres, int filmId) {
        genres.forEach(genre -> jdbcTemplate.execute(String.format("MERGE INTO FILM_GENRES (film_id, genre_id) " +
                "VALUES (%d, %d)", filmId, genre.getId())));
    }

    private List<Genre> getFilmGenres(Integer filmId) {
        List<Genre> genresList = new ArrayList<>();
        jdbcTemplate.query("SELECT genre_id FROM FILM_GENRES WHERE film_id = " + filmId, rs -> {
            do {
                int genreId = rs.getInt("genre_id");
                genresList.add(genreStorage.getGenre(genreId));
            } while (rs.next());
        });
        return genresList;
    }
}
