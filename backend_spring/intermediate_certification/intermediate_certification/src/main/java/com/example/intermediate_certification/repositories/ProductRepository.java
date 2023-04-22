package com.example.intermediate_certification.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.intermediate_certification.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%',:name,'%')) ORDER BY p.name")
    List<Product> findByNameOrderByName(@Param("name") String name);
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%',:name,'%')) ORDER BY p.price")
    List<Product> findByNameOrderByPrice(@Param("name") String name);
}
