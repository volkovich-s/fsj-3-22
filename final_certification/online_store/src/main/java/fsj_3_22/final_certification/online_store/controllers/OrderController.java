package fsj_3_22.final_certification.online_store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import fsj_3_22.final_certification.online_store.models.Order;
import fsj_3_22.final_certification.online_store.services.UserService;
import fsj_3_22.final_certification.online_store.services.ProductService;
import fsj_3_22.final_certification.online_store.services.OrderService;

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
  public String cart(Model model) {
    final var user = userService.getCurrent();
    model.addAttribute("user", user);
    model.addAttribute("all", orderService.getInCart(user));
    return "/cart/all";
  }
  @PostMapping("cart/{id}/add")
  public String cart_add(@PathVariable int id) {
    final var user = userService.getCurrent();
    final var product = productService.getById(id);
    var order = orderService.getInCart(user, product);
    if(order == null) {
      order = new Order();
      order.setUser(user);
      order.setProduct(product);
      // TODO: add method for cart.
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
    final var order = orderService.getInCart(user, product);
    order.setCount(order.getCount() - 1);
    if(order.getCount() > 0)
      orderService.update(order);
    else
      orderService.delete(order.getId());
    return "redirect:/cart?removed={id}";
  }
  @GetMapping("order")
  public String order(Model model) {
    final var user = userService.getCurrent();
    model.addAttribute("user", user);
    model.addAttribute("all", orderService.getNotInCart(user));
    return "/order/all";
  }
  @PostMapping("order/{id}")
  public String order_add(@PathVariable int id) {
    final var order = orderService.getById(id);
    // TODO: to method.
    order.setState(orderService.getStateByName("STORAGE"));
    orderService.update(order);
    return "redirect:/cart?ordered={id}";
  }
  @GetMapping("/order/admin")
  public String order_admin(@RequestParam(required = false, defaultValue = "") String id, Model model) {
    model.addAttribute("user", userService.getCurrent());
    if(id.isEmpty())
      model.addAttribute("all", orderService.getAll());
    else
      model.addAttribute("all", orderService.searchById(Integer.parseInt(id)));
    model.addAttribute("states", orderService.getStates());
    return "order/admin/all";
  }
  @PostMapping("/order/admin/{id}/state")
  public String order_admin_state(@PathVariable int id, @RequestParam int state) {
    final var order = orderService.getById(id);
    order.setState(orderService.getStateById(state));
    orderService.update(order);
    return "redirect:/order/admin";
  }
}
