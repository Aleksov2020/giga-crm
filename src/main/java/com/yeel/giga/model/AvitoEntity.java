package com.yeel.giga.model;

import com.yeel.giga.enums.AvitoEntityStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvitoEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String link;
    private String squareHouse;
    private String squareHome;
    private String price;
    private String pricePerSquare;
    private String phone;
    private String scope;
    private AvitoEntityStatus avitoEntityStatus;
}
