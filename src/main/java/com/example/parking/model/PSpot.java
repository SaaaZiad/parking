package com.example.parking.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pspots")
public class PSpot {
  @Id
  @Column(name = "pspot_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pspotId;
  @NotBlank
  private String description;
  private boolean isBooked=false;
  @Min(1)
  private Long price;
  @OneToMany(mappedBy = "pspot")
  private List<Order> orders;
}
