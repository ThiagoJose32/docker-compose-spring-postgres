package com.ifal.devops.api.controller;

import com.ifal.devops.api.model.Animal;
import com.ifal.devops.api.service.AnimalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public List<Animal> getAll() {
        return animalService.getAll();
    }

    @PostMapping
    public Animal create (@RequestBody Animal animal) {
        return animalService.save(animal);
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id) {
        animalService.delete(id);
    }

    @GetMapping("/{id}")
    public Animal getById(@PathVariable Long id) {
        return animalService.getById(id);
    }

    @PutMapping("/{id}")
    public Animal update(@PathVariable Long id, @RequestBody Animal animal) {
        return animalService.update(id, animal);
    }


}
