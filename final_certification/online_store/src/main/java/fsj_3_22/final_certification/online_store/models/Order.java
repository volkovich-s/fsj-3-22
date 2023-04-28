package fsj_3_22.final_certification.online_store.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_order")
public class Order extends bModel {
  @ManyToOne(optional = false)
  @JoinColumn(nullable = false)
  private User user;
  public User getUser() {
    return user;
  }
  public void setUser(@NonNull User user) {
    this.user = user;
  }
  @ManyToOne(optional = false)
  @JoinColumn(nullable = false)
  private Product product;
  public Product getProduct() {
    return product;
  }
  public void setProduct(@NonNull Product product) {
    this.product = product;
  }
  @Column
  private int count;
  public int getCount() {
    return count;
  }
  public void setCount(int count) {
    this.count = count;
  }
  @ManyToOne(optional = false)
  @JoinColumn(nullable = false)
  private OrderState state;
  public OrderState getState() {
    return state;
  }
  @Column
  private LocalDateTime beginDateTime;
  public LocalDateTime getBeginDateTime() {
    return beginDateTime;
  }
  public void setBeginDateTime(LocalDateTime beginDateTime) {
    this.beginDateTime = beginDateTime;
  }
  @Column
  private LocalDateTime endDateTime;
  public LocalDateTime getEndDateTime() {
    return endDateTime;
  }
  public void setEndDateTime(LocalDateTime endDateTime) {
    this.endDateTime = endDateTime;
  }
  public void setState(@NonNull OrderState state) {
    this.state = state;
  }
}
