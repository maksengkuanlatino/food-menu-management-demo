package com.example.demo.controller;

import com.example.demo.model.FoodMenu;
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
    private FoodMenuService service; // Controller calls Service layer directly

	@GetMapping
    public String viewMenuPage(Model model) {
        model.addAttribute("menuItems", service.getAllItems());
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

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id, 
                             @RequestParam("token") String token) { 
        service.deleteItem(id);
        return "redirect:/menu?token=" + token; 
    }

}
