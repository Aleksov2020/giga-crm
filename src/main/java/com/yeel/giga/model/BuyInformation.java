package com.yeel.giga.model;

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
public class BuyInformation {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private BigDecimal result;
}
