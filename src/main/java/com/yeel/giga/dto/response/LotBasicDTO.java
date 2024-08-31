package com.yeel.giga.dto.response;

import com.yeel.giga.enums.LotStatus;
import com.yeel.giga.enums.LotType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotBasicDTO {
    private Long id;
    private String name;
    private String address;
    private LocalDate endDate;
    private LocalDate createdDate;
    private BigDecimal zadatok;
    private LocalDate dateZadatok;
    private LotType lotType;
    private LotStatus lotStatus;
    private String comment;
    private BigDecimal result;
    private BigDecimal ourPrice;
}
