package com.example.intermediate_certification.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import com.example.intermediate_certification.models.Order;
import com.example.intermediate_certification.services.UserService;
import com.example.intermediate_certification.services.ProductService;
import com.example.intermediate_certification.services.OrderService;

@Controller
public class OrderController {
  private final UserService userService;
  private final ProductService productService;
  private final OrderService orderService;
  public OrderController(@NonNull UserService userService, @NonNull ProductService productService, @NonNull OrderService orderService) {
    this.userService = userService;
    this.productService = productService;
    this.orderService = orderService;
  }
  @GetMapping("cart")
  public ModelAndView cart() {
    final var user = userService.getCurrent();
    final var toReturn = new ModelAndView("/cart/all");
    toReturn.addObject("user", user);
    toReturn.addObject("all", orderService.getByUser(user));
    return toReturn;
  }
  @PostMapping("cart/{id}")
  public String cart_add(@PathVariable int id) {
    final var user = userService.getCurrent();
    final var product = productService.getById(id);
    var order = orderService.getByUserAndProduct(user, product);
    if(order == null) {
      order = new Order();
      order.setUser(user);
      order.setProduct(product);
      order.setState(orderService.getStateByName("CART"));
    }
    order.setCount(order.getCount() + 1);
    orderService.update(order);
    return "redirect:/product?added={id}";
  }
  @PostMapping("cart/{id}/remove")
  public String cart_remove(@PathVariable int id) {
    final var user = userService.getCurrent();
    final var product = productService.getById(id);
    final var order = orderService.getByUserAndProduct(user, product);
    order.setCount(order.getCount() - 1);
    if(order.getCount() > 0)
      orderService.update(order);
    else
      orderService.delete(order.getId());
    return "redirect:/cart?removed";
  }
  @GetMapping("order")
  public ModelAndView order() {
    final var user = userService.getCurrent();
    final var toReturn = new ModelAndView("/order/all");
    toReturn.addObject("user", user);
    toReturn.addObject("all", orderService.getByUser(user));
    return toReturn;
  }
  @PostMapping("order/{id}")
  public String order_add(@PathVariable int id) {
    final var order = orderService.getById(id);
    order.setState(orderService.getStateByName("STORAGE"));
    orderService.update(order);
    return "redirect:/cart?ordered";
  }
  @GetMapping("/order/admin")
  public ModelAndView order_admin(@RequestParam(required = false, defaultValue = "") String id) {
    final var toReturn = new ModelAndView("order/admin/all");
    toReturn.addObject("user", userService.getCurrent());
    if(id.isEmpty())
      toReturn.addObject("all", orderService.getAll());
    else
      toReturn.addObject("all", orderService.searchById(Integer.parseInt(id)));
    toReturn.addObject("states", orderService.getStates());
    return toReturn;
  }
  @PostMapping("/order/admin/{id}/state")
  public String order_admin_state(@PathVariable int id, @RequestParam int state) {
    final var order = orderService.getById(id);
    order.setState(orderService.getStateById(state));
    orderService.update(order);
    return "redirect:/order/admin";
  }
}
