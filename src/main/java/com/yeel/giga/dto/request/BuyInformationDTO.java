package com.yeel.giga.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyInformationDTO {
    private Long id;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private BigDecimal result;
}
