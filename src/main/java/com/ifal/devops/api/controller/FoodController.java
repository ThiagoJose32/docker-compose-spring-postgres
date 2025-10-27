package com.ifal.devops.api.controller;

import com.ifal.devops.api.model.Food;
import com.ifal.devops.api.service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public List<Food> getAll() {return foodService.getAll();}

    @PostMapping
    public Food create (@RequestBody Food food) {return foodService.save(food);}
}
