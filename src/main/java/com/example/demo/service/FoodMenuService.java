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
	
	@Value("${upload.path}")
	private String uploadPath;

    public List<FoodMenu> getAllItems() {
        return repository.findAll();
    }

    public FoodMenu getItemById(Long id) {
        return repository.findById(id).orElse(null);
    }
    
    public void createItem(FoodMenu item, String categoryName, MultipartFile imageFile) throws IOException{
        // Find existing pre-populated category entity or fallback
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found!"));
        item.setCategory(category);
        if (imageFile != null && !imageFile.isEmpty()) {
            
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

          
            String uniqueFilename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            File saveFile = new File(uploadPath + uniqueFilename);
            
            imageFile.transferTo(saveFile);
  
            item.setImageName(uniqueFilename);
        }
        
        repository.save(item);
    }
    
    
    
    public void updateItem(Long id ,FoodMenu item, String categoryName, MultipartFile imageFile) throws IOException{
    
    	FoodMenu existingItem = repository.findById(id).orElse(null);
    	Category category = categoryRepository.findByName(categoryName)
                 .orElseThrow(() -> new RuntimeException("Category not found!"));
      
    	if (existingItem != null) {
            existingItem.setName(item.getName());
            existingItem.setPrice(item.getPrice());
            existingItem.setDescription(item.getDescription());
            existingItem.setCategory(category);
            if (imageFile != null && !imageFile.isEmpty()) {
        
            	File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String uniqueFilename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                File saveFile = new File(uploadPath + uniqueFilename);
                imageFile.transferTo(saveFile);
                existingItem.setImageName(uniqueFilename);
            }
            
            repository.save(existingItem);
        }
    }
    
    
    
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }
	
}
