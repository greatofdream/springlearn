package com.example.tacocloud;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
@Entity
@Table(name = "Taco_Order")
public class Order {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  private Date placedAt;
  //end::allButValidation[]
  @NotBlank(message="Name is required")
  //tag::allButValidation[]
  private String deliveryName;
  //end::allButValidation[]

  @NotBlank(message="Street is required")
  //tag::allButValidation[]
  private String deliveryStreet;
  //end::allButValidation[]

  @NotBlank(message="City is required")
  //tag::allButValidation[]
  private String deliveryCity;
  //end::allButValidation[]

  @NotBlank(message="State is required")
  //tag::allButValidation[]
  private String deliveryState;
  //end::allButValidation[]

  @NotBlank(message="Zip code is required")
  //tag::allButValidation[]
  private String deliveryZip;
  //end::allButValidation[]

  @CreditCardNumber(message="Not a valid credit card number")
  //tag::allButValidation[]
  private String ccNumber;
  //end::allButValidation[]

  @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
           message="Must be formatted MM/YY")
  //tag::allButValidation[]
  private String ccExpiration;
  //end::allButValidation[]

  @Digits(integer=3, fraction=0, message="Invalid CVV")
  //tag::allButValidation[]
  private String ccCVV;
  @ManyToMany(targetEntity = Taco.class)
  private List<Taco> tacos = new ArrayList<>();
  public void addDesign(Taco design) {
    this.tacos.add(design);
  }
  @PrePersist
  void placedAt() {
    this.placedAt = new Date();
  }
}
//end::allButValidation[]
//end::all[]
