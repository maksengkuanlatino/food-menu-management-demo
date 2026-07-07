package com.example.demo.repository;

import com.example.demo.model.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFoodRepository extends JpaRepository<FoodMenu, Long> {
}
