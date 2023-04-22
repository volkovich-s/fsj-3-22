package com.example.intermediate_certification.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import com.example.intermediate_certification.models.Product;
import com.example.intermediate_certification.repositories.ProductRepository;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(@NonNull ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> getAll() {
        return productRepository.findAll();
    }
    public Product getById(int id) {
        return productRepository.findById(id).orElse(null);
    }
    public List<Product> getByNameOrderByName(@NonNull String name) {
        return productRepository.findByNameOrderByName(name);
    }
    public List<Product> getByNameOrderByPrice(@NonNull String name) {
        return productRepository.findByNameOrderByPrice(name);
    }
    @Transactional
    public void create(@NonNull Product product) {
        productRepository.save(product);
    }
    @Transactional
    public void update(@NonNull Product product) {
        productRepository.save(product);
    }
    @Transactional
    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
