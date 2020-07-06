package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.model.Category;
import ru.geekbrains.service.CategoryService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RequestMapping("/category")
@Controller
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("new")
    public String createCategory(Model model) {
        model.addAttribute("create", true);
        model.addAttribute("category", new Category());
        return "category";
    }

    @GetMapping
    public String userList(Model model)
    {
        List<Category> categoryPage = categoryService.findAll();
        model.addAttribute("categoriesPage", categoryPage);
        return "categories";
    }

    @PostMapping
    public String saveCategory(Category category, BindingResult bindingResult) {
       if (bindingResult.hasErrors()) {
            return "category";
        }
        categoryService.save(category);
        return "redirect:/category";
    }

    @GetMapping("edit/{id}")
    public String editCategoryById(@PathVariable(name = "id") Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        model.addAttribute("category", category.get());
        return "category";
    }

    @DeleteMapping
    public String deleteCategory(@RequestParam("id") Long id) {
        categoryService.delete(id);
        return "redirect:/category";
    }

}
