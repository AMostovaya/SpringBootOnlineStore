package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Category;
import ru.geekbrains.repo.BrandRepository;
import ru.geekbrains.repo.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    private BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Transactional(readOnly = true)
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Brand> findById(long id) {

        return brandRepository.findById(id);
    }

    @Transactional
    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    public void delete(Long id) { brandRepository.deleteById(id);
    }
}
