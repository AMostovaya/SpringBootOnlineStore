package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.CategoryRepository;
import ru.geekbrains.service.ProductService;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/product")
@Controller
public class ProductController {

    private ProductService productService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "product";
    }

    @GetMapping
    public String productList(Model model)
    {
        List<Product> productPage = productService.findAll();
        model.addAttribute("usersPage", productPage);
        return "products";
    }

    @PostMapping
    public String saveProduct(Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product";
        }
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("edit/{id}")
    public String editProductById(@PathVariable(name = "id") Long id, Model model) {

        Optional<Product> product = productService.findById(id);
        model.addAttribute("product", product.get());
        model.addAttribute("categories", categoryRepository.findAll());
        return "update-category";
    }

    @PostMapping("update/{id}")
    public String updateProduct(@PathVariable("id") long id, @Valid Product product,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            return "update-product";
        }

        productService.save(product);
        model.addAttribute("products", productService.findAll());
        return "redirect:/product";
    }

    @DeleteMapping
    public String deleteProduct(@RequestParam("id") Long id) {

        productService.delete(id);
        return "redirect:/product";

    }


}
