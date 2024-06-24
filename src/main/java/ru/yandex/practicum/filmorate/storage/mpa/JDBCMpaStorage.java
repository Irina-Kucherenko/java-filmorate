package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Primary
public class JDBCMpaStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    private final MpaMapper mpaMapper;

    @Override
    public MPA getMpa(Integer id) {
        return jdbcTemplate.query("SELECT * FROM MPA WHERE mpa_id = " + id, rs -> {
            if (!rs.next()) {
                throw new ResourceNotFoundException("MPA не найден.");
            }
            return mpaMapper.mapRow(rs, rs.getRow());
        });
    }

    @Override
    public List<MPA> getMpaList() {
        List<MPA> mpaList = new ArrayList<>();
        jdbcTemplate.query("SELECT * FROM MPA", rs -> {
            do {
                MPA mpa = mpaMapper.mapRow(rs, rs.getRow());
                if (mpa != null) {
                    mpaList.add(mpa);
                }
            } while (rs.next());
        });
        return mpaList;
    }
}
