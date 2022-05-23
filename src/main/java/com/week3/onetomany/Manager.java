package com.week3.onetomany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "otm_manager")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="mid")
  private Long id;
  @Column(name="manager_email")
  private String email;
  @Column(name="manager_name")
  private String name;
  @Column(name="manager_phone")
  private String phone;

  @OneToMany(mappedBy = "manager",cascade = CascadeType.ALL)
  private List<Customer> customers = new ArrayList<>();

}
