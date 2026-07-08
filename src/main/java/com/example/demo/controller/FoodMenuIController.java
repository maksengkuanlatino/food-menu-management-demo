package com.example.demo.controller;

import com.example.demo.model.FoodMenu;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.service.FoodMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/menu")
@CrossOrigin(origins = "*")

public class FoodMenuIController {
	@Autowired
    private FoodMenuService service; 
	@Autowired
    private ICategoryRepository categoryRepository;
	@GetMapping
    public String viewMenuPage(Model model, @RequestParam("token") String token) {
        model.addAttribute("menuItems", service.getAllItems());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("token", token);
        model.addAttribute("item", new FoodMenu()); 
        model.addAttribute("isEdit", false);
        return "menu";
    }

//	@PostMapping("/add")
//	public String createItem(@ModelAttribute FoodMenu item, @RequestParam("categoryName") String categoryName) {
//        service.createItem(item, categoryName);
//        return "redirect:/menu";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteItem(@PathVariable Long id) {
//        service.deleteItem(id);
//        return "redirect:/menu";
//    }
    
	@PostMapping("/add")
	public String createItem(@ModelAttribute FoodMenu item, 
	                         @RequestParam("categoryName") String categoryName,
	                         @RequestParam("imageFile") MultipartFile imageFile, // Catches file stream input
	                         @RequestParam("token") String token) throws IOException {
	    service.createItem(item, categoryName, imageFile);
	    return "redirect:/menu?token=" + token;

    }

	@PostMapping("update/{id}")
	public String updateItem(@PathVariable Long id,
            @ModelAttribute FoodMenu item,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("token") String token) throws IOException {
	    service.updateItem(id,item, categoryName, imageFile);
	    return "redirect:/menu?token=" + token;

    }
	
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model, @RequestParam("token") String token) {
	 
	    FoodMenu existingItem = service.getItemById(id);
	    
	    if (existingItem != null) {
	        model.addAttribute("menuItems", service.getAllItems());
	        model.addAttribute("categories", categoryRepository.findAll());
	        model.addAttribute("token", token);
	        
	        model.addAttribute("item", existingItem); // Pass the populated item into the form
	        model.addAttribute("isEdit", true);       // Set the flag to true to trigger edit mode
	        
	        return "menu"; // Reuses your single menu.html file!
	    }
	    
	    return "redirect:/menu?token=" + token;
	}
	
	
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id, 
                             @RequestParam("token") String token) { 
        service.deleteItem(id);
        return "redirect:/menu?token=" + token; 
    }

}
