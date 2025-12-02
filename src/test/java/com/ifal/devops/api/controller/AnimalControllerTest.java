package com.ifal.devops.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifal.devops.api.model.Animal;
import com.ifal.devops.api.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
@WebMvcTest(AnimalController.class)
@ActiveProfiles("test")
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public AnimalService animalService() {
            return Mockito.mock(AnimalService.class);
        }
    }

    private Animal buildAnimal(Long id) {
        Animal a = new Animal();
        a.setId(id);
        a.setNome("Rex");
        a.setFoto("foto.jpg");
        a.setSexo('M');
        a.setEspecie("Cão");
        a.setRaca("Vira-lata");
        a.setDataNascimento(LocalDate.of(2020, 1, 1));
        a.setDescricao("Muito brincalhão");
        a.setCastrado(true);
        a.setStatus("DISPONIVEL");
        a.setAdotante(null);
        a.setCriadoPor("sistema");
        a.setCriadoEm(LocalDate.of(2024, 1, 1));
        a.setModificadoPor(null);
        a.setModificadoEm(null);
        return a;
    }


    @Test
    void shouldReturnAllAnimals() throws Exception {
        List<Animal> animals = List.of(buildAnimal(1L), buildAnimal(2L));

        when(animalService.getAll()).thenReturn(animals);

        mockMvc.perform(get("/animal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void shouldCreateAnimal() throws Exception {
        Animal req = buildAnimal(null);
        Animal saved = buildAnimal(1L);

        when(animalService.save(any(Animal.class))).thenReturn(saved);

        mockMvc.perform(
                        post("/animal")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void shouldDeleteAnimal() throws Exception {
        doNothing().when(animalService).delete(1L);

        mockMvc.perform(delete("/animal/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnAnimalById() throws Exception {
        Animal animal = buildAnimal(1L);

        when(animalService.getById(1L)).thenReturn(animal);

        mockMvc.perform(get("/animal/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Rex"));
    }

    @Test
    void shouldUpdateAnimal() throws Exception {
        Animal req = buildAnimal(1L);
        req.setNome("Max"); // Alterando o nome

        Animal updated = buildAnimal(1L);
        updated.setNome("Max");

        // Mocka o método update, que recebe id e o animal modificado
        when(animalService.update(any(Long.class), any(Animal.class))).thenReturn(updated);

        mockMvc.perform(
                        put("/animal/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Max"));
    }

}
