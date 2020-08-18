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
import ru.geekbrains.repo.BrandRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestPropertySource(locations = "classpath:applicationTest.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class BrandControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BrandRepository brandRepository;

    @BeforeEach
    public void init() {
        brandRepository.deleteAllInBatch();
    }

    // добавление нового бренда
    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    //@Disabled пропуск теста в случае не стабильности
    public void addNewBrandTest() throws Exception {
        mvc.perform(post("/brand")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "-1")
                .param("name", "New brand")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/brands"));

        Brand brand = new Brand();
        brand.setName("New brand");
        Optional<Brand> actualBrand = brandRepository.findOne(Example.of(brand));

        assertTrue(actualBrand.isPresent());
        assertEquals("New brand", actualBrand.get().getName());
    }

    // удаление бренда по id
    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void deleteBrandTest() throws Exception {
        // предварительно создадим новый с нужным id
        Brand brand = new Brand();
        brand.setName("New brand");
        Long id = brandRepository.save(brand).getId();

        assertTrue(brandRepository.existsById(id));

        mvc.perform(delete("/brand/{id}/delete", brand.getId())
             .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/brands"));
        assertFalse(brandRepository.existsById(id));
    }

}
