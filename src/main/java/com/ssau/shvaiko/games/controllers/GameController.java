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
    public Set<Game> getAll(@RequestParam(required = false) Map<String, String> filter) {
        return filter(filter, gameRepository.findAll(), Game.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> get(@PathVariable(name = "id") UUID id) {
        Optional<Game> optional = gameRepository.findById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Game game) {
        List<Platform> platforms = Collections.emptyList();
        List<Publisher> publishers = Collections.emptyList();
        List<Genre> genres = Collections.emptyList();
        if(game.getPlatforms() != null) {
            platforms = platformRepository.findAllById(game.getPlatforms().stream().map(Platform::getId).collect(Collectors.toList()));

        }
        if(game.getGenres() != null) {
            genres = genreRepository.findAllById(game.getGenres().stream().map(Genre::getId).collect(Collectors.toList()));
        }

        if(game.getPublishers() != null) {
            publishers = publisherRepository.findAllById(game.getPublishers().stream().map(Publisher::getId).collect(Collectors.toList()));
        }
        Optional<Developer> developer = game.getDeveloper() != null ? developerRepository.findById(game.getDeveloper().getId()) : Optional.empty();

        game.setPlatforms(new HashSet<>(platforms));
        game.setPublishers(new HashSet<>(publishers));
        game.setGenres(new HashSet<>(genres));
        developer.ifPresent(game::setDeveloper);

        gameRepository.save(game);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Game dto, @PathVariable UUID id) {
        Optional<Game> wrapper = gameRepository.findById(id);
        if(wrapper.isPresent()) {
            List<Platform> platforms = Collections.emptyList();
            List<Publisher> publishers = Collections.emptyList();
            List<Genre> genres = Collections.emptyList();
            if(dto.getPlatforms() != null) {
                platforms = platformRepository.findAllById(dto.getPlatforms().stream().map(Platform::getId).collect(Collectors.toList()));

            }
            if(dto.getGenres() != null) {
                genres = genreRepository.findAllById(dto.getGenres().stream().map(Genre::getId).collect(Collectors.toList()));
            }

            if(dto.getPublishers() != null) {
               publishers = publisherRepository.findAllById(dto.getPublishers().stream().map(Publisher::getId).collect(Collectors.toList()));
            }

            Optional<Developer> developer = dto.getDeveloper() != null ? developerRepository.findById(dto.getDeveloper().getId()) : Optional.empty();

            Game game = wrapper.get();
            game.update(dto);
            game.setPlatforms(new HashSet<>(platforms));
            game.setPublishers(new HashSet<>(publishers));
            game.setGenres(new HashSet<>(genres));
            developer.ifPresent(game::setDeveloper);

            gameRepository.save(game);


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

