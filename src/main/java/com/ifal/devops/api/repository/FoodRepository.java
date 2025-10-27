package com.ifal.devops.api.repository;

import com.ifal.devops.api.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
