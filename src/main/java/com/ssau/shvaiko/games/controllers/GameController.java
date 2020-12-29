package com.ssau.shvaiko.games.controllers;

import com.ssau.shvaiko.games.dto.GameDTO;
import com.ssau.shvaiko.games.models.*;
import com.ssau.shvaiko.games.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ssau.shvaiko.games.config.AppConstants.API_V1;

@RequestMapping(API_V1 + "/games")
@CrossOrigin
@RestController
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @GetMapping
    public Set<GameDTO> getAll(@RequestParam(required = false) Map<String, String> filter) {
        return filter(filter, gameRepository.findAll(), Game.class).stream().map(GameDTO::new).collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> get(@PathVariable(name = "id") UUID id) {
        Optional<Game> optional = gameRepository.findById(id);
        return optional.map(game -> ResponseEntity.ok(new GameDTO(game)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity add(@RequestBody GameDTO dto) {
        List<Platform> platforms = platformRepository.findAllById(dto.getPlatforms());
        List<Publisher> publishers = publisherRepository.findAllById(dto.getPublishers());
        List<Genre> genres = genreRepository.findAllById(dto.getGenres());
        Optional<Developer> developer = developerRepository.findById(dto.getDeveloper());

        Game game = new Game(dto);
        game.setPlatforms(new HashSet<>(platforms));
        game.setPublishers(new HashSet<>(publishers));
        game.setGenres(new HashSet<>(genres));

        gameRepository.save(game);

        if(developer.isPresent()) {
            developer.get().getGames().add(game);
            developerRepository.save(developer.get());
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody GameDTO dto, @PathVariable UUID id) {
        Optional<Game> wrapper = gameRepository.findById(id);
        if(wrapper.isPresent()) {
            List<Platform> platforms = platformRepository.findAllById(dto.getPlatforms());
            List<Publisher> publishers = publisherRepository.findAllById(dto.getPublishers());
            List<Genre> genres = genreRepository.findAllById(dto.getGenres());
            Optional<Developer> developer = developerRepository.findById(dto.getDeveloper());

            Game game = wrapper.get();
            game.update(dto);
            game.setPlatforms(new HashSet<>(platforms));
            game.setPublishers(new HashSet<>(publishers));
            game.setGenres(new HashSet<>(genres));

            gameRepository.save(game);

            if(developer.isPresent()) {
                developer.get().getGames().add(game);
                developerRepository.save(developer.get());
            }

            return ResponseEntity.ok().build();
        }
       return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") UUID id) {
        gameRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    protected <E> Set<E> filter(Map<String, String> filter, Collection<E> data, Class<E> clazz) {
        Stream<E> stream = data.stream();
        for (Map.Entry<String, String> filterEntry : filter.entrySet()) {
            stream = stream.filter(t -> {
                try {
                    return byField(t, clazz, filterEntry.getKey(), filterEntry.getValue());
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return false;
            });
        }

        return stream.collect(Collectors.toSet());
    }

    protected <E> boolean byField(E object, Class<E> clazz, String fieldName, String fieldValue) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = clazz.getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        String realValue = String.valueOf(declaredField.get(object));
        return realValue.equalsIgnoreCase(fieldValue);
    }

}

