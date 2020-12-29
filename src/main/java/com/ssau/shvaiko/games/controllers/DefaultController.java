package com.ssau.shvaiko.games.controllers;

import com.ssau.shvaiko.games.models.Updatable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class DefaultController<T extends Updatable<T>> {

    protected JpaRepository<T, UUID> repository;
    private Class<T> clazz;

    protected DefaultController(JpaRepository<T, UUID> repository, Class<T> clazz) {
        this.repository = repository;
        this.clazz = clazz;
    }

    @GetMapping
    public Set<T> getAll(@RequestParam(required = false) Map<String, String> filter) {
        return filter(filter, repository.findAll(), clazz);
    }

    @GetMapping("/byIds")
    public Set<T> getAllByIds(@RequestBody List<UUID> uuids) {
        return filter(null, repository.findAllById(uuids), clazz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> get(@PathVariable(name = "id") UUID id) {
        Optional<T> optional = repository.findById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity add(@RequestBody T dto) {
        repository.save(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody T dto, @PathVariable UUID id) {
        Optional<T> wrapper = repository.findById(id);
        if (wrapper.isPresent()) {
            T entity = wrapper.get();
            entity.update(dto);
            repository.save(entity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") UUID id) {
        repository.deleteById(id);
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
