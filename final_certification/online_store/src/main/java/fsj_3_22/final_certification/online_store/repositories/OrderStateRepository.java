package fsj_3_22.final_certification.online_store.repositories;

import fsj_3_22.final_certification.online_store.models.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStateRepository extends JpaRepository<OrderState, Integer> {
  OrderState findByName(@NonNull String name);
}
