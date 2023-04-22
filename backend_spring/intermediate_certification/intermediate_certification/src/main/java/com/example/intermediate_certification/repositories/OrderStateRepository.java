package com.example.intermediate_certification.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import com.example.intermediate_certification.models.OrderState;

@Repository
public interface OrderStateRepository extends JpaRepository<OrderState, Integer> {
  OrderState findByName(@NonNull String name);
}
