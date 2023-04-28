package fsj_3_22.final_certification.online_store.controllers;

import java.util.Collections;
import java.util.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import javax.security.auth.Subject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import fsj_3_22.final_certification.online_store.models.User;
import fsj_3_22.final_certification.online_store.services.UserService;

@Controller
public class AuthenticationController {
    private class UserAuthentication implements Authentication {
        private final User user;
        public UserAuthentication(User user) {
            this.user = user;
        }
        @Override
        public Object getPrincipal() {
            return user.getName();
        }
        @Override
        public Object getCredentials() {
            return user.getPassword();
        }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
        }
        @Override
        public String getName() {
            return user.getName();
        }
        @Override
        public Object getDetails() {
            return null;
        }
        @Override
        public boolean isAuthenticated() {
            return true;
        }
        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }
        @Override
        public boolean implies(Subject subject) {
            return Authentication.super.implies(subject);
        }
    }
    private final UserService userService;
    public AuthenticationController(@NonNull UserService userService) {
        this.userService = userService;
    }
    private void authenticate(@NonNull HttpServletRequest request, @NonNull User user) {
        final var sc = SecurityContextHolder.getContext();
        sc.setAuthentication(new UserAuthentication(userService.getByName(user.getName())));
        request.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
    }
    @GetMapping("/authentication/sign_in")
    public String signIn(Model model) {
        model.addAttribute("user_to_sign_in", new User());
        return "authentication/sign_in";
    }
    @PostMapping("/authentication/sign_in")
    public String signIn(HttpServletRequest request, @ModelAttribute(name = "user_to_sign_in") User user, BindingResult br) {
        if(user.getName().isEmpty())
            br.rejectValue("name", "","Введите имя!");
        else if(user.getName().length() < 2 || user.getName().length() > 64)
            br.rejectValue("name", "","Имя должно быть от 2 до 64 символов!");
        else if(userService.getByName(user.getName()) == null)
            br.rejectValue("name", "", "Пользователь с именем не существует!");
        if(user.getPassword().isEmpty())
            br.rejectValue("password", "", "Введите пароль!");
        else if(user.getPassword().length() < 2 || user.getPassword().length() > 64)
            br.rejectValue("password", "", "Пароль должен быть от 2 до 64 символов!");
        else if(userService.getByName(user.getName()) != null && !userService.checkPassword(user.getName(), user.getPassword()))
            br.rejectValue("password", "", "Неверный пароль!");
        if(br.hasErrors())
            return "authentication/sign_in";
        authenticate(request, user);
        return "redirect:/";
    }
    @GetMapping("/authentication/sign_up")
    public String signUp(Model model) {
        model.addAttribute("user", null);
        model.addAttribute("user_to_sign_up", new User());
        return "authentication/sign_up";
    }
    @PostMapping("/authentication/sign_up")
    public String signUp(HttpServletRequest request, @ModelAttribute(name = "user_to_sign_up") User user, BindingResult br) {
        if(user.getName().isEmpty())
            br.rejectValue("name", "","Введите имя!");
        else if(user.getName().length() < 2 || user.getName().length() > 64)
            br.rejectValue("name", "","Имя должно быть от 2 до 64 символов!");
        else if(userService.getByName(user.getName()) != null)
            br.rejectValue("name", "", "Имя уже используется!");
        if(user.getPassword().isEmpty())
            br.rejectValue("password", "", "Введите пароль!");
        else if(user.getPassword().length() < 2 || user.getPassword().length() > 64)
            br.rejectValue("password", "", "Пароль должен быть от 2 до 64 символов!");
        if(br.hasErrors())
            return "authentication/sign_up";
        userService.create(user);
        authenticate(request, user);
        return "redirect:/";
    }
    @GetMapping("/authentication/sign_out")
    public String signOut() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/";
    }
}
