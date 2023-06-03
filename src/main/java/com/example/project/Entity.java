package com.example.project;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@jakarta.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "attribute")
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double fundFreq;
    private Double zcr;
    private String label;

//    công thức euclid
    public double calculateDistance(double fundFreq, double zcr) {
        return Math.sqrt(Math.pow(fundFreq - this.fundFreq, 2) + Math.pow(zcr - this.zcr, 2));
    }
}
