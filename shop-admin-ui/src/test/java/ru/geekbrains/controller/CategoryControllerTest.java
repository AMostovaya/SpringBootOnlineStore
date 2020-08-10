package ru.geekbrains.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Category;
import ru.geekbrains.repo.CategoryRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestPropertySource(locations = "classpath:applicationTest.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CategoryRepository categoryRepository;

    // добавление новой категории
    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void addNewCategoryTest() throws Exception {
        mvc.perform(get("/category/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("name", "New category")
                .with(csrf()))
                .andExpect(status().isOk());
        Category category = new Category();
        category.setName("New category");
        categoryRepository.save(category);
        Optional<Category> actualCategory = categoryRepository.findOne(Example.of(category));

        assertTrue(actualCategory.isPresent());
        assertEquals("New category", actualCategory.get().getName());

    }

    // удаление категории по id
    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void deleteCategoryTest() throws Exception {
        // предварительно создадим новый с нужным id
        Category category = new Category();
        category.setName("New category");
        categoryRepository.save(category);

        mvc.perform(delete("/category/delete/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/category"));
    }

}
