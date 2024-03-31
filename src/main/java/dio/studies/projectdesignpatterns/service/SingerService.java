package dio.studies.projectdesignpatterns.service;

import dio.studies.projectdesignpatterns.model.Music;
import dio.studies.projectdesignpatterns.model.Singer;

import java.util.Optional;

public interface SingerService {
    Iterable<Singer> findAll();
    Optional<Singer> findSingerById(Long id);
    void create(Singer singer);
    void update(Long id, Singer singer);
    void delete(Long id);
    void createWithMusic(Singer singer);
}
