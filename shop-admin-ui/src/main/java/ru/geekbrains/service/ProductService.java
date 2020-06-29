package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.model.Product;
import ru.geekbrains.model.User;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.repo.UserRepository;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(long id) {

        return productRepository.findById(id);
    }


    @Transactional
    public void save(Product product) {

        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
