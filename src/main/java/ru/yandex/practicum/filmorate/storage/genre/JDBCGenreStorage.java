package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Component
@Primary
public class JDBCGenreStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    private final GenreMapper genreMapper;

    @Override
    public Genre getGenre(Integer id) {
        return jdbcTemplate.query("SELECT * FROM GENRES WHERE genre_id = " + id, rs -> {
            if (!rs.next()) {
                throw new ResourceNotFoundException("Жанр не найден.");
            }
            return genreMapper.mapRow(rs, rs.getRow());
        });
    }

    @Override
    public List<Genre> getGenreList() {
        List<Genre> genreList = new ArrayList<>();
        jdbcTemplate.query("SELECT * FROM GENRES", rs -> {
            do {
                Genre genre = genreMapper.mapRow(rs, rs.getRow());
                if (genre != null) {
                    genreList.add(genre);
                }
            } while (rs.next());
        });
        return genreList;
    }
}
