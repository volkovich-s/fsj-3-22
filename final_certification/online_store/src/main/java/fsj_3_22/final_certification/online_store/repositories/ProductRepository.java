package fsj_3_22.final_certification.online_store.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import fsj_3_22.final_certification.online_store.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByName(@NonNull String name);
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%',:name,'%')) ORDER BY p.name")
    List<Product> searchByNameOrderByName(@Param("name") String name);
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%',:name,'%')) ORDER BY p.price")
    List<Product> searchByNameOrderByPrice(@Param("name") String name);
}
