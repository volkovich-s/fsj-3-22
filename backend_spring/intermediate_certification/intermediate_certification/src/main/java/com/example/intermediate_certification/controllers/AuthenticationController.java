package com.example.intermediate_certification.controllers;

import java.util.Collection;
import java.util.Collections;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.Validator;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import javax.security.auth.Subject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.example.intermediate_certification.models.User;
import com.example.intermediate_certification.services.UserService;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
public class AuthenticationController {
    private abstract class bValidator implements Validator {
        protected final UserService userService;
        protected bValidator(UserService userService) {
            this.userService = userService;
        }
        @Override
        public boolean supports(Class<?> clazz) {
            return User.class.equals(clazz);
        }
    }
    @Component
    private class SignInValidator extends bValidator {
        public SignInValidator(UserService userService) {
            super(userService);
        }
        @Override
        public void validate(Object target, Errors errors) {
            final var user = (User)target;
            if (user.getName().isEmpty())
                errors.rejectValue("name", "", "Введите имя!");
            else if (userService.getByName(user.getName()) == null)
                errors.rejectValue("name", "", "Неверное имя!");
            else if (user.getPassword().isEmpty())
                errors.rejectValue("password", "", "Введите пароль!");
            else if (!userService.checkPassword(user.getName(), user.getPassword()))
                errors.rejectValue("password", "", "Неверный пароль!");
        }
    }
    @Component
    private class SignUpValidator extends bValidator {
        public SignUpValidator(UserService userService) {
            super(userService);
        }
        @Override
        public void validate(Object target, Errors errors) {
            final var user = (User)target;
            if (user.getName().isEmpty())
                errors.rejectValue("name", "", "Введите имя!");
            else if (userService.getByName(user.getName()) != null)
                errors.rejectValue("name", "", "Пользователь с таким именем уже существует!");
            else if (user.getPassword().isEmpty())
                errors.rejectValue("password", "", "Введите пароль!");
            else if (user.getPassword().length() < 8 || user.getPassword().length() >= 256)
                errors.rejectValue("password", "", "Пароль должен быть от 8 до 256 символов.");
        }
    }
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
    private final SignInValidator signInValidator;
    private final SignUpValidator signUpValidator;
    public AuthenticationController(@NonNull UserService userService, @Lazy @NonNull SignInValidator signInValidator, @Lazy @NonNull SignUpValidator signUpValidator) {
        this.userService = userService;
        this.signInValidator = signInValidator;
        this.signUpValidator = signUpValidator;
    }
    @GetMapping("/authentication/sign_in")
    public String signIn(@ModelAttribute User user) {
        return "authentication/sign_in";
    }
    @PostMapping("/authentication/sign_in")
    public String signIn(HttpServletRequest request, @ModelAttribute @Valid User user, BindingResult br) {
        signInValidator.validate(user, br);
        if (br.hasErrors())
            return "authentication/sign_in";
        final var sc = SecurityContextHolder.getContext();
        sc.setAuthentication(new UserAuthentication(userService.getByName(user.getName())));
        request.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
        return "redirect:/";
    }
    @GetMapping("/authentication/sign_up")
    public String signUp(@ModelAttribute User user) {
        return "authentication/sign_up";
    }
    @PostMapping("/authentication/sign_up")
    public String signUp(HttpServletRequest request, @ModelAttribute @Valid User user, BindingResult br) {
        signUpValidator.validate(user, br);
        if (br.hasErrors())
            return "authentication/sign_up";
        userService.create(user);
        final var sc = SecurityContextHolder.getContext();
        sc.setAuthentication(new UserAuthentication(userService.getByName(user.getName())));
        request.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
        return "redirect:/";
    }
    @PostMapping("/authentication/sign_out")
    public String signOut() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/";
    }
}
