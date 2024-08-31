package com.yeel.giga.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LotMoneyDTO {
    private Long id;
    private BigDecimal zadatok;
    private BigDecimal stavka;
    private BigDecimal price;
    private LocalDate dateZadatok;
}
