package com.yeel.giga.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LotMoney {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal zadatok;
    private BigDecimal stavka;
    private BigDecimal price;
    private LocalDate dateZadatok;
}
