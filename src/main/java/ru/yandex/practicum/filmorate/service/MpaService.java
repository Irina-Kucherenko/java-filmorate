package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {

    private final MpaStorage mpaStorage;

    public MPA getMpa(Integer id) {
        return mpaStorage.getMpa(id);
    }

    public List<MPA> getMpaList() {
        return mpaStorage.getMpaList();
    }
}
