package fsj_3_22.final_certification.online_store.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import fsj_3_22.final_certification.online_store.models.UserRole;
import fsj_3_22.final_certification.online_store.models.User;
import fsj_3_22.final_certification.online_store.repositories.EnumRepository;
import fsj_3_22.final_certification.online_store.repositories.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final EnumRepository<UserRole> userRoleRepository;
    public List<UserRole> getRoles() {
        return userRoleRepository.findAll();
    }
    public UserRole getRoleById(int id) {
        return userRoleRepository.findById(id).orElse(null);
    }
    public UserRole getRoleByName(@NonNull String name) {
        return userRoleRepository.findByName(name);
    }
    private final UserRepository userRepository;
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
    private final PasswordEncoder passwordEncoder;
    public boolean checkPassword(@NonNull String name, @NonNull String password) {
        return passwordEncoder.matches(password, getByName(name).getPassword());
    }
    @Transactional
    public void create(@NonNull User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(getAll().isEmpty() ? userRoleRepository.findByName("ROLE_ADMIN") : userRoleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }
    @Transactional
    public void update(@NonNull User user) {
        userRepository.save(user);
    }
    public UserService(@NonNull EnumRepository<UserRole> userRoleRepository, @NonNull UserRepository userRepository) {
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
}
