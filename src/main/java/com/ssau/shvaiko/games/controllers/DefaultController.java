package com.ssau.shvaiko.games.controllers;

import com.ssau.shvaiko.games.models.Updatable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class DefaultController<T extends Updatable<T>> {

    private JpaRepository<T, UUID> repository;

    protected DefaultController(JpaRepository<T, UUID> repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<T> getAll() {
        return repository.findAll();
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
}
