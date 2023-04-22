package com.example.intermediate_certification.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "application_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Column(nullable = false, unique = true, length = 64)
    private String name;
    public String getName() {
        return name;
    }
    public void setName(@NonNull String name) {
        this.name = name;
    }
    @Column(nullable = false, length = 256)
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(@NonNull String password) {
        this.password = password;
    }
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private UserRole role;
    public UserRole getRole() {
        return role;
    }
    public void setRole(@NonNull UserRole role) {
        this.role = role;
    }
}
