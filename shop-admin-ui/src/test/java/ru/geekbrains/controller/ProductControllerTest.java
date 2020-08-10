package ru.geekbrains.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.ProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(locations = "classpath:applicationTest.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    // добавление нового продукта
    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void addNewProductTest() throws Exception {
        mvc.perform(get("/product/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("name", "New product")
                .with(csrf()))
                .andExpect(status().isOk());
//        Brand brand = new Brand();
//        brand.setId(1L);
//        brand.setName("New brand");
//
//        Category category = new Category();
//        category.setId(1L);
//        category.setName("New category");
//
//        Product product = new Product();
//        product.setId(1L);
//        product.setName("New product");
//        product.setBrand(brand);
//        product.setCategory(category);
//        productRepository.save(product);
//        Optional<Product> actualProduct = productRepository.findOne(Example.of(product));
//
//        assertTrue(actualProduct.isPresent());
//        assertEquals("New product", actualProduct.get().getName());

    }
}
