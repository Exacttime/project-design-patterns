package dio.studies.projectdesignpatterns.service.impl;

import dio.studies.projectdesignpatterns.model.Music;
import dio.studies.projectdesignpatterns.model.MusicRepository;
import dio.studies.projectdesignpatterns.model.Singer;
import dio.studies.projectdesignpatterns.model.SingerRepository;
import dio.studies.projectdesignpatterns.service.SingerService;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    private SingerRepository repository;
    @Autowired
    private MusicRepository musicRepository;
    @Override
    public Iterable<Singer> findAll() {return repository.findAll();}
    @Override
    public Optional<Singer> findSingerById(Long id) {return repository.findById(id);}
    @Override
    public void create(Singer singer) {repository.save(singer);}
    @Override
    public void createWithMusic(Singer singer) {saveWithMusic(singer);}
    @Override
    public void update(Long id, Singer singer) {
        if(!repository.existsById(id)){
            throw new OpenApiResourceNotFoundException("Singer not found");
        }
        singer.setId(id); // Set the id to ensure we're updating the correct singer
        saveWithMusic(singer);
    }
    @Override
    public void delete(Long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new OpenApiResourceNotFoundException("Singer not found");
        }
    }
    private void saveWithMusic(Singer singer) {
        // Save the singer first so it gets an id
        Singer savedSinger = repository.save(singer);

        List<Music> musics = singer.getMusic();
        if (musics != null) {
            for (Music music : musics) {
                // Set the singerId of the music to be the id of the saved singer
                music.setSingerId(savedSinger.getId());
                musicRepository.save(music);
            }
        }

        // Update the music list of the singer and save it again
        savedSinger.setMusic(musics);
        repository.save(savedSinger);
    }
}