package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.repo.BrandRepository;
import ru.geekbrains.repo.CategoryRepository;
import ru.geekbrains.service.ProductService;
import javax.validation.Valid;
import java.io.IOException;


@RequestMapping("/product")
@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public ProductController(ProductService productService, CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        model.addAttribute("product", new ProductRepr());
        model.addAttribute("create", true);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product";
    }

    @GetMapping
    public String productList(Model model)
    {
        model.addAttribute("productsPage", productService.findAll());
        model.addAttribute("activePage", "Products");
        return "products";
    }

    @PostMapping
    public String saveProduct(ProductRepr product, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "product";
        }
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("edit/{id}")
    public String editProductById(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id).orElseThrow(NotFoundException::new));
        model.addAttribute("edit", true);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product";
    }

    @PostMapping("update/{id}")
    public String updateProduct(@PathVariable("id") long id, @Valid ProductRepr product,
                             BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            product.setId(id);
            return "product";
        }

        productService.save(product);
        model.addAttribute("products", productService.findAll());
        return "redirect:/product";
    }

    @DeleteMapping
    public String deleteProduct(@RequestParam("id") Long id) {

        productService.deleteById(id);
        return "redirect:/product";

    }


}
