package com.example.intermediate_certification.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "application_order")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
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
  public void setState(@NonNull OrderState state) {
    this.state = state;
  }
}
