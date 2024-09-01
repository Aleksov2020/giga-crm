package com.yeel.giga.dto.request.appRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvitoEntityDetailDTO {
    @JsonProperty("adName")
    private String adName;

    @JsonProperty("price")
    private String price;

    @JsonProperty("pricePerMeter")
    private String pricePerMeter;

    @JsonProperty("squareField")
    private String squareField;

    @JsonProperty("squareHouse")
    private String squareHouse;

    @JsonProperty("viewsCount")
    private String viewsCount;
}
