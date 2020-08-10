package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Role;
import ru.geekbrains.repo.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Category> findById(long id) {

        return categoryRepository.findById(id);
    }

    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void delete(Long id) { categoryRepository.deleteById(id);
    }
}
