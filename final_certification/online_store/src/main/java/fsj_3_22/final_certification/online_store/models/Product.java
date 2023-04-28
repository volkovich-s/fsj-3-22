package fsj_3_22.final_certification.online_store.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
public class Product extends bModel {
    @Column(nullable = false, length = 256)
    private String name;
    public String getName() {
        return name;
    }
    public void setName(@NonNull String name) {
        this.name = name;
    }
    @Column(nullable = false, columnDefinition = "text")
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(@NonNull String description) {
        this.description = description;
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
