package fsj_3_22.final_certification.online_store.services;

import java.util.ArrayList;
import java.util.List;

import fsj_3_22.final_certification.online_store.repositories.OrderStateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import fsj_3_22.final_certification.online_store.models.*;
import fsj_3_22.final_certification.online_store.repositories.OrderRepository;

@Service
@Transactional(readOnly = true)
public class OrderService {
  private final OrderStateRepository orderStateRepository;
  public List<OrderState> getStates() {
    return orderStateRepository.findAll();
  }
  public OrderState getStateById(int id) {
    return orderStateRepository.findById(id).orElse(null);
  }
  public OrderState getStateByName(@NonNull String name) {
    return orderStateRepository.findByName(name);
  }
  private final OrderRepository orderRepository;
  public List<Order> getAll() {
    return orderRepository.findAll();
  }
  public Order getById(int id) {
    return orderRepository.findById(id).orElse(null);
  }
  public List<Order> getByUser(@NonNull User user) {
    return orderRepository.findByUser(user);
  }
  public List<Order> getByUserAndProduct(@NonNull User user, @NonNull Product product) {
    return orderRepository.findByUserAndProduct(user, product);
  }
  public List<Order> getInCart(@NonNull User user) {
    final var toReturn = new ArrayList<Order>();
    for(final var order : getByUser(user))
      // TODO: add method for cart.
      if(order.getState().getName().equals("CART"))
        toReturn.add(order);
    return toReturn;
  }
  public List<Order> getNotInCart(@NonNull User user) {
    final var toReturn = new ArrayList<Order>();
    for(final var order : getByUser(user))
      // TODO: add method for cart.
      if(!order.getState().getName().equals("CART"))
        toReturn.add(order);
    return toReturn;
  }
  public Order getInCart(@NonNull User user, @NonNull Product product) {
    for(final var order : getByUserAndProduct(user, product))
      // TODO: add method for cart.
      if(order.getState().getName().equals("CART"))
        return order;
    return null;
  }
  public List<Order> searchById(int id) {
    return orderRepository.searchById(id);
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
}
