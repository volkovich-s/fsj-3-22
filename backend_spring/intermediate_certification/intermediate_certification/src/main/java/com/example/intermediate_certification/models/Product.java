package com.example.intermediate_certification.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Column(nullable = false, length = 256)
    private String name;
    public String getName() {
        return name;
    }
    public void setName(@NonNull String name) {
        this.name = name;
    }
    @Column(nullable = false)
    private double price;
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    @Column(nullable = false)
    private String image;
    public String getImage() {
        return image;
    }
    public void setImage(@NonNull String image) {
        this.image = image;
    }
}
