package fsj_3_22.final_certification.online_store.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import fsj_3_22.final_certification.online_store.models.User;
import fsj_3_22.final_certification.online_store.models.Product;
import fsj_3_22.final_certification.online_store.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
  List<Order> findByUser(@NonNull User user);
  List<Order> findByUserAndProduct(@NonNull User user, @NonNull Product product);
  @Query("SELECT o FROM Order o WHERE CAST(o.id as string) LIKE concat('%',CAST(:id as string))")
  List<Order> searchById(@Param("id") int id);
}
