package dio.studies.projectdesignpatterns.controller;

import dio.studies.projectdesignpatterns.model.Music;
import dio.studies.projectdesignpatterns.model.MusicRepository;
import dio.studies.projectdesignpatterns.model.Singer;
import dio.studies.projectdesignpatterns.service.SingerService;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("singers")
public class SingerRestController {
    @Autowired
    private SingerService singerService;
    @Autowired
    private MusicRepository musicRepository;

    @GetMapping
    public ResponseEntity<Iterable<Singer>> buscarTodos() {
        return ResponseEntity.ok(singerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Singer>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(singerService.findSingerById(id));
    }

    @PostMapping
    public ResponseEntity<Singer> inserir(@RequestBody Singer singer) {
        singerService.create(singer);
        return ResponseEntity.ok(singer);
    }

    @PostMapping("/withMusic")
    public ResponseEntity<Singer> inserirWithMusic(@RequestBody Singer singer) {
        singerService.createWithMusic(singer);
        return ResponseEntity.ok(singer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Singer> atualizar(@PathVariable Long id, @RequestBody Singer singer) {
        singerService.update(id, singer);
        return ResponseEntity.ok(singer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        singerService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/music")
    public ResponseEntity<Singer> addMusicToSinger(@PathVariable Long id, @RequestBody Music music) {
        music.setSingerId(id);
        musicRepository.save(music);
        Singer singer = singerService.findSingerById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Singer not found"));
        return ResponseEntity.ok(singer);
    }
}