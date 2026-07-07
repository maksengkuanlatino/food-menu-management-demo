package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Will store "Appetizer", "Main", or "Dessert"

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<FoodMenu> foodItems;

    // Constructors
    public Category() {}
    public Category(String name) { this.name = name; }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<FoodMenu> getFoodItems() { return foodItems; }
    public void setFoodItems(List<FoodMenu> foodItems) { this.foodItems = foodItems; }
}