package fsj_3_22.final_certification.online_store.models;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class bModel {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private int id;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
}
