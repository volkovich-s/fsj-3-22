package fsj_3_22.final_certification.online_store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import fsj_3_22.final_certification.online_store.configs.ContentConfig;
import fsj_3_22.final_certification.online_store.models.Product;
import fsj_3_22.final_certification.online_store.services.UserService;
import fsj_3_22.final_certification.online_store.services.ProductService;
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
    public String all(@RequestParam(required = false, defaultValue = "") String name, @RequestParam(required = false, defaultValue = "name") String sorting, Model model) {
        model.addAttribute("user", userService.getCurrent());
        switch (sorting) {
            case "name":
                model.addAttribute("all", productService.searchByNameOrderByName(name));
                break;
            case "price":
                model.addAttribute("all", productService.searchByNameOrderByPrice(name));
                break;
        }
        return "product/all";
    }
    @GetMapping("/product/{id}")
    public String one(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getCurrent());
        model.addAttribute("one", productService.getById(id));
        return "product/one";
    }
    @GetMapping("/product/admin")
    public String admin_all(@RequestParam(required = false, defaultValue = "") String name, @RequestParam(required = false, defaultValue = "name") String sorting, Model model) {
        model.addAttribute("user", userService.getCurrent());
        switch (sorting) {
            case "name":
                model.addAttribute("all", productService.searchByNameOrderByName(name));
                break;
            case "price":
                model.addAttribute("all", productService.searchByNameOrderByPrice(name));
                break;
        }
        return "product/admin/all";
    }
    @GetMapping("/product/admin/{id}")
    public String admin_one(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getCurrent());
        if(id == 0) {
            final var one = new Product();
            one.setId(0);
            one.setName("");
            one.setDescription("");
            one.setImage("none.png");
            one.setPrice(0);
            model.addAttribute("one", one);
        } else {
            model.addAttribute("one", productService.getById(id));
        }
        return "product/admin/one";
    }
    @PostMapping("/product/admin/{id}")
    public String admin_one(@PathVariable int id, @RequestParam(required = false) MultipartFile image_file, Model model, @ModelAttribute(name = "one") Product product, BindingResult br) {
        if(product.getName().isEmpty())
            br.rejectValue("name", "", "Введите название!");
        else if(product.getName().length() < 2 || product.getName().length() > 64)
            br.rejectValue("name", "", "Название должно быть от 2 до 64 символов!");
        else if(id == 0 && productService.get(product.getName()) != null)
            br.rejectValue("name", "", "Продукт с названием уже существует!");
        if(id == 0 && image_file.isEmpty())
            br.rejectValue("image", "", "Выберите изображение!");
        if(product.getPrice() <= 0)
            br.rejectValue("price", "", "Цена должна быть больше 0!");
        if(br.hasErrors()) {
            model.addAttribute("user", userService.getCurrent());
            return "product/admin/one";
        }
        if(!image_file.isEmpty()) {
            product.setImage(product.getName() + "." + StringUtils.getFilenameExtension(image_file.getOriginalFilename()));
            try {
                contentConfig.SaveImage("product/" + product.getImage(), image_file.getBytes());
            }
            catch (IOException e) {

            }
        } else {
            product.setImage(productService.getById(id).getImage());
        }
        if(id == 0)
          productService.create(product);
        else
          productService.update(product);
        return id == 0 ? "redirect:/product/admin?created={id}" : "redirect:/product/admin?updated={id}";
    }
    @PostMapping("product/admin/{id}/delete")
    public String admin_delete(@PathVariable int id) {
        final var product = productService.getById(id);
        contentConfig.DeleteImage("product/" + product.getImage());
        productService.delete(product.getId());
        return "redirect:/product/admin?deleted";
    }
}
