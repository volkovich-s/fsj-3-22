package com.example.intermediate_certification.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
public class UserRole {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  @Column(unique = true, nullable = false)
  private String name;
  public String getName() {
    return name;
  }
  public void setName(@NonNull String name) {
    this.name = name;
  }
}
