package com.ifal.devops.api.service;

import com.ifal.devops.api.model.Food;
import com.ifal.devops.api.repository.FoodRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> getAll() { return foodRepository.findAll(); }

    public Food save(Food food) { return foodRepository.save(food); }

    public void delete(Long id) { foodRepository.deleteById(id); }

    public Food updatePartial(Long id, Food partial) {
        Food current = foodRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found"));

        if (partial.getName() != null) current.setName(partial.getName());
        if (partial.getExpirationDate() != null) current.setExpirationDate(partial.getExpirationDate());
        if (partial.getQuantity() != null) current.setQuantity(partial.getQuantity());

        return foodRepository.save(current);
    }
}