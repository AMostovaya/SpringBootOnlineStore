package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.ProductRepository;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImplement();
        ((ProductServiceImplement) productService).setProductRepository(productRepository);
    }

    @Test
    public void findByIdTest() {
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setName("Category1");

        Brand expectedBrand = new Brand();
        expectedBrand.setId(1L);
        expectedBrand.setName("Brand1");

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product1");
        expectedProduct.setPictures(new ArrayList<>());
        expectedProduct.setPrice(new BigDecimal(123));
        expectedProduct.setBrand(expectedBrand);
        expectedProduct.setCategory(expectedCategory);
        when(productRepository.findById(eq(1l))).thenReturn(Optional.of(expectedProduct));

        Optional<ProductRepr> opt = productService.findById(1L);
        assertTrue(opt.isPresent());
        assertEquals(expectedProduct.getId(), opt.get().getId());
        assertEquals(expectedProduct.getName(), opt.get().getName());
        assertEquals(expectedCategory.getName(), opt.get().getCategoryName().getName());
        assertEquals(expectedBrand.getName(), opt.get().getBrandName().getName());
    }
}
