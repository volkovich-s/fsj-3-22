package com.example.intermediate_certification.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.intermediate_certification.models.UserRole;
import com.example.intermediate_certification.models.User;
import com.example.intermediate_certification.repositories.UserRoleRepository;
import com.example.intermediate_certification.repositories.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(@NonNull UserRoleRepository userRoleRepository, @NonNull UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        if(userRoleRepository.findByName("ROLE_USER") == null) {
            final var default_user = new UserRole();
            default_user.setName("ROLE_USER");
            userRoleRepository.save(default_user);
        }
        if(userRoleRepository.findByName("ROLE_ADMIN") == null) {
            final var default_admin = new UserRole();
            default_admin.setName("ROLE_ADMIN");
            userRoleRepository.save(default_admin);
        }
    }
    public List<UserRole> getRoles() {
        return userRoleRepository.findAll();
    }
    public UserRole getRoleById(int id) {
        return userRoleRepository.findById(id).orElse(null);
    }
    public UserRole getRoleByName(@NonNull String name) {
        return userRoleRepository.findByName(name);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public User getById(int id) {
        return userRepository.findById(id).orElse(null);
    }
    public User getByName(@NonNull String name) {
        return userRepository.findByName(name);
    }
    public User getCurrent() {
        return getByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    public boolean checkPassword(@NonNull String name, @NonNull String password) {
        return passwordEncoder.matches(password, getByName(name).getPassword());
    }
    @Transactional
    public void create(@NonNull User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(getAll().isEmpty())
            user.setRole(userRoleRepository.findByName("ROLE_ADMIN"));
        else
            user.setRole(userRoleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }
    @Transactional
    public void update(User user) {
        userRepository.save(user);
    }
}
