package com.horecarobot.backend.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.fontys.horecarobot.databaselibrary.models.Category;

@RestController
@CrossOrigin(origins = {"http://localhost:8081", "https://rigs.bryanaafjes.nl", "https://rigs.bryanaafjes.nl:444"})
@RequestMapping(path = "/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Iterable<Category> getAllCategories() {
        return this.categoryService.getAllCategories();
    }

    @PostMapping
    public void createCategory(@RequestBody Category category) {
        this.categoryService.createCategory(category);
    }
}
