package fsj_3_22.final_certification.online_store.models;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class bEnumModel extends bModel {
  @Column(unique = true, nullable = false)
  private String name;
  public String getName() {
    return name;
  }
  public void setName(@NonNull String name) {
    this.name = name;
  }
}
