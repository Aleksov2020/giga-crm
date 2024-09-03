package com.yeel.giga.dto.request.appRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yeel.giga.enums.AvitoEntityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvitoEntityDetailDTO {
    private Long id;

    @JsonProperty("adName")
    private String adName;

    @JsonProperty("price")
    private String price;

    @JsonProperty("pricePerMeter")
    private String pricePerMeter;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("squareField")
    private String squareField;

    @JsonProperty("squareHouse")
    private String squareHouse;

    @JsonProperty("viewsCount")
    private String viewsCount;

    @JsonProperty("status")
    private AvitoEntityStatus avitoEntityStatus;
}
