package fsj_3_22.final_certification.online_store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import fsj_3_22.final_certification.online_store.services.UserService;

@Controller
public class UserController {
    private final UserService userService;
    public UserController(@NonNull UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/user/admin")
    public String admin_all(Model model) {
        model.addAttribute("user", userService.getCurrent());
        model.addAttribute("all", userService.getAll());
        model.addAttribute("roles", userService.getRoles());
        return "user/admin/all";
    }
    @PostMapping("/user/admin/{id}/role")
    public String admin_role(@PathVariable int id, @RequestParam int role) {
        final var user = userService.getById(id);
        if(user != userService.getCurrent()) {
            user.setRole(userService.getRoleById(role));
            userService.update(user);
        }
        return "redirect:/user/admin";
    }
}
