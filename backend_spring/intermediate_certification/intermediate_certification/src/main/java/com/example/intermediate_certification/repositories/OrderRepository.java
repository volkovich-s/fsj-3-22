package com.example.intermediate_certification.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.intermediate_certification.models.Order;
import com.example.intermediate_certification.models.User;
import com.example.intermediate_certification.models.Product;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
  List<Order> findByUser(@NonNull User user);
  Order findByUserAndProduct(@NonNull User user, @NonNull Product product);
  @Query("SELECT o FROM Order o WHERE CAST(o.id as string) LIKE concat('%',:id)")
  List<Order> searchById(@Param("id") @NonNull String id);
}
