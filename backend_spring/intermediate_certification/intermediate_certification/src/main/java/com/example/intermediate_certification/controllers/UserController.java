package com.example.intermediate_certification.controllers;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import com.example.intermediate_certification.services.UserService;

@Controller
public class UserController {
    private final UserService userService;
    public UserController(@NonNull UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/user/admin")
    public ModelAndView admin_all() {
        final var toReturn = new ModelAndView("user/admin/all");
        toReturn.addObject("user", userService.getCurrent());
        toReturn.addObject("all", userService.getAll());
        toReturn.addObject("roles", userService.getRoles());
        return toReturn;
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
