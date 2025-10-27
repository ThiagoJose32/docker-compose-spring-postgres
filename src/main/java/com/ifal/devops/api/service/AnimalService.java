package com.ifal.devops.api.service;

import com.ifal.devops.api.model.Animal;
import com.ifal.devops.api.repository.AnimalRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    // Listar
    public List<Animal> getAll() {return animalRepository.findAll();}

    // Criar
    public Animal save(Animal animal) {return animalRepository.save(animal);}

    // Deletar
    @DeleteMapping("/{id}")
    public void delete(Long id) {animalRepository.deleteById(id);}
}
