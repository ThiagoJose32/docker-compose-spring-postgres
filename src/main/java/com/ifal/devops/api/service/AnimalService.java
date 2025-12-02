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

    public Animal getById(Long id) {
        return animalRepository.findById(id).orElse(null);
    }

    public Animal update(Long id, Animal animal) {
        Animal existing = animalRepository.findById(id).orElse(null);
        if (existing != null) {
            // Atualize os campos necessários — um exemplo:
            existing.setNome(animal.getNome());
            existing.setFoto(animal.getFoto());
            existing.setSexo(animal.getSexo());
            existing.setEspecie(animal.getEspecie());
            existing.setRaca(animal.getRaca());
            existing.setDataNascimento(animal.getDataNascimento());
            existing.setDescricao(animal.getDescricao());
            existing.setCastrado(animal.getCastrado());
            existing.setStatus(animal.getStatus());
            existing.setAdotante(animal.getAdotante());
            existing.setModificadoPor(animal.getModificadoPor());
            existing.setModificadoEm(animal.getModificadoEm());

            return animalRepository.save(existing);
        }
        return null; // ou lançar exceção, conforme sua lógica
    }

}
