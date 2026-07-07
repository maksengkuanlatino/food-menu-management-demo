package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.FoodMenu;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.repository.IFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

@Service
public class FoodMenuService {

	@Autowired
    private IFoodRepository repository;
	
	@Autowired
    private ICategoryRepository categoryRepository;

    public List<FoodMenu> getAllItems() {
        return repository.findAll();
    }

    public FoodMenu createItem(FoodMenu item) {
        return repository.save(item);
    }

    @Value("${upload.path}")
    private String uploadPath;
    
    public void createItem(FoodMenu item, String categoryName, MultipartFile imageFile) throws IOException{
        // Find existing pre-populated category entity or fallback
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found!"));
        item.setCategory(category);
        if (imageFile != null && !imageFile.isEmpty()) {
            // Ensure the directory structure exists
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Generate a uniquely secure random filename wrapper string
            String uniqueFilename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            File saveFile = new File(uploadPath + uniqueFilename);
            
            // Save raw file bytes directly to disk storage
            imageFile.transferTo(saveFile);
            
            // Track the filename string inside the PostgreSQL entity map
            item.setImageName(uniqueFilename);
        }
        
        repository.save(item);
    }

    public void deleteItem(Long id) {
        repository.deleteById(id);
    }
	
}
