package com.example.demo.config;

import com.example.demo.model.Category;
import com.example.demo.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
      
    	List<String> defaultCategories =  new ArrayList<String> ();
    	defaultCategories.add("Appetizer");
    	defaultCategories.add("Main");
    	defaultCategories.add("Dessert");
    	
        for (String categoryName : defaultCategories) {
            // Only insert if the category doesn't exist in PostgreSQL yet
            if (categoryRepository.findByName(categoryName).isEmpty()) {
                Category category = new Category();
                category.setName(categoryName);
                categoryRepository.save(category);
                System.out.println("Pre-seeded category: " + categoryName);
            }
        }
    }
}