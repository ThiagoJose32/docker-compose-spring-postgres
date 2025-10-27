package com.ifal.devops.api.service;

import com.ifal.devops.api.model.Food;
import com.ifal.devops.api.repository.FoodRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    // Listar
    public List<Food> getAll() {return foodRepository.findAll();}

    // Criar
    public Food save(Food food) {return foodRepository.save(food);}

    // Deletar
    @DeleteMapping("/{id}")
    public void delete(Long id) {foodRepository.deleteById(id);}
}
