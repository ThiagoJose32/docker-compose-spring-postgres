package com.ifal.devops.api.repository;

import com.ifal.devops.api.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
