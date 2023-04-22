package com.example.intermediate_certification.controllers;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.intermediate_certification.configs.ContentConfig;
import com.example.intermediate_certification.models.Product;
import com.example.intermediate_certification.services.UserService;
import com.example.intermediate_certification.services.ProductService;
import java.io.IOException;

@Controller
public class ProductController {
    private final ContentConfig contentConfig;
    private final UserService userService;
    private final ProductService productService;
    public ProductController(@NonNull ContentConfig contentConfig, @NonNull UserService userService, @NonNull ProductService productService) {
        this.contentConfig = contentConfig;
        this.userService = userService;
        this.productService = productService;
    }
    @GetMapping("/product")
    public ModelAndView all(@RequestParam(required = false, defaultValue = "") String name, @RequestParam(required = false, defaultValue = "name") String sorting) {
        final var toReturn = new ModelAndView("product/all");
        toReturn.addObject("user", userService.getCurrent());
        switch (sorting) {
            case "name":
                toReturn.addObject("all", productService.getByNameOrderByName(name));
                break;
            case "price":
                toReturn.addObject("all", productService.getByNameOrderByPrice(name));
                break;
        }
        return toReturn;
    }
    @GetMapping("/product/{id}")
    public ModelAndView one(@PathVariable int id) {
        final var product = productService.getById(id);
        final var toReturn = new ModelAndView(product != null ? "product/one" : "product/none");
        toReturn.addObject("user", userService.getCurrent());
        toReturn.addObject("one", product);
        return toReturn;
    }
    @GetMapping("/product/admin")
    public ModelAndView admin_all(@RequestParam(required = false, defaultValue = "") String name, @RequestParam(required = false, defaultValue = "name") String sorting) {
        final var toReturn = new ModelAndView("product/admin/all");
        toReturn.addObject("user", userService.getCurrent());
        switch (sorting) {
            case "name":
                toReturn.addObject("all", productService.getByNameOrderByName(name));
                break;
            case "price":
                toReturn.addObject("all", productService.getByNameOrderByPrice(name));
                break;
        }
        return toReturn;
    }
    @GetMapping("/product/admin/{id}")
    public ModelAndView admin_edit(@PathVariable int id) {
        final var product = productService.getById(id);
        final var toReturn = new ModelAndView(product != null ? "product/admin/one" : "product/admin/none");
        toReturn.addObject("user", userService.getCurrent());
        toReturn.addObject("one", product);
        return toReturn;
    }
    @PostMapping("/product/admin/{id}")
    public String admin_edit(@ModelAttribute Product product, @RequestParam(required = false) MultipartFile image_file) throws IOException {
        if(!image_file.isEmpty()) {
            product.setImage("product/" + product.getName() + "." + StringUtils.getFilenameExtension(image_file.getOriginalFilename()));
            contentConfig.SaveImage(product.getImage(), image_file.getBytes());
        } else {
            product.setImage(productService.getById(product.getId()).getImage());
        }
        productService.update(product);
        return "redirect:/product/admin/{id}";
    }
    @GetMapping("/product/admin/create")
    public ModelAndView admin_create() {
        final var toReturn = new ModelAndView("product/admin/create");
        toReturn.addObject("user", userService.getCurrent());
        toReturn.addObject("one", new Product());
        return toReturn;
    }
    @PostMapping("/product/admin/create")
    public String admin_create(@ModelAttribute Product product, @RequestParam MultipartFile image_file) throws IOException {
        product.setImage("product/" + product.getName() + "." + StringUtils.getFilenameExtension(image_file.getOriginalFilename()));
        contentConfig.SaveImage(product.getImage(), image_file.getBytes());
        productService.create(product);
        return "redirect:/product/admin?created";
    }
    @PostMapping("/product/admin/{id}/delete")
    public String admin_delete(@PathVariable int id) {
        final var product = productService.getById(id);
        contentConfig.DeleteImage(product.getImage());
        productService.delete(product.getId());
        return "redirect:/product/admin?deleted";
    }
}
