package com.example.intermediate_certification.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import com.example.intermediate_certification.models.*;
import com.example.intermediate_certification.repositories.OrderStateRepository;
import com.example.intermediate_certification.repositories.OrderRepository;

@Service
@Transactional(readOnly = true)
public class OrderService {
  private final OrderStateRepository orderStateRepository;
  private final OrderRepository orderRepository;

  public OrderService(@NonNull OrderStateRepository orderStateRepository, @NonNull OrderRepository orderRepository) {
    this.orderStateRepository = orderStateRepository;
    this.orderRepository = orderRepository;
    if(orderStateRepository.findByName("CART") == null) {
      final var state = new OrderState();
      state.setName("CART");
      orderStateRepository.save(state);
    }
    if(orderStateRepository.findByName("STORAGE") == null) {
      final var state = new OrderState();
      state.setName("STORAGE");
      orderStateRepository.save(state);
    }
    if(orderStateRepository.findByName("TRANSIT") == null) {
      final var state = new OrderState();
      state.setName("TRANSIT");
      orderStateRepository.save(state);
    }
    if(orderStateRepository.findByName("AWAITING") == null) {
      final var state = new OrderState();
      state.setName("AWAITING");
      orderStateRepository.save(state);
    }
    if(orderStateRepository.findByName("RECEIVED") == null) {
      final var state = new OrderState();
      state.setName("RECEIVED");
      orderStateRepository.save(state);
    }
  }
  public List<OrderState> getStates() {
    return orderStateRepository.findAll();
  }
  public OrderState getStateById(int id) {
    return orderStateRepository.findById(id).orElse(null);
  }
  public OrderState getStateByName(@NonNull String name) {
    return orderStateRepository.findByName(name);
  }
  public List<Order> getAll() {
    return orderRepository.findAll();
  }
  public Order getById(int id) {
    return orderRepository.findById(id).orElse(null);
  }
  public List<Order> getByUser(@NonNull User user) {
    return orderRepository.findByUser(user);
  }
  public Order getByUserAndProduct(@NonNull User user, @NonNull Product product) {
    return orderRepository.findByUserAndProduct(user, product);
  }
  public List<Order> searchById(int id) {
    return orderRepository.searchById(Integer.toString(id));
  }
  @Transactional
  public void create(@NonNull Order order) {
    orderRepository.save(order);
  }
  @Transactional
  public void update(@NonNull Order order) {
    orderRepository.save(order);
  }
  @Transactional
  public void delete(int id) {
    orderRepository.deleteById(id);
  }
}
