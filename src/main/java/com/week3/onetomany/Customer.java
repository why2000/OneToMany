package com.week3.onetomany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "otm_customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="cid")
  private Long id;
  @Column(name= "customer_email")
  private String email;
  @Column(name="customer_name")
  private String name;
  @Column(name="customer_age")
  private int age;
  @Column(name="customer_phone")
  private String phone;


  @ManyToOne(targetEntity = Manager.class,fetch = FetchType.LAZY)
  @JoinColumn(name = "assigned_mid",referencedColumnName = "mid")
  private Manager manager;

}
