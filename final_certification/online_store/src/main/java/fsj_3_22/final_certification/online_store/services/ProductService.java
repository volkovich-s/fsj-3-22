package fsj_3_22.final_certification.online_store.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import fsj_3_22.final_certification.online_store.models.Product;
import fsj_3_22.final_certification.online_store.repositories.ProductRepository;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> getAll() {
        return productRepository.findAll();
    }
    public Product getById(int id) {
        return productRepository.findById(id).orElse(null);
    }
    public Product get(@NonNull String name) {
        return productRepository.findByName(name);
    }
    public List<Product> searchByNameOrderByName(@NonNull String name) {
        return productRepository.searchByNameOrderByName(name);
    }
    public List<Product> searchByNameOrderByPrice(@NonNull String name) {
        return productRepository.searchByNameOrderByPrice(name);
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
    public ProductService(@NonNull ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
