package com.yeel.giga.dto.request.appRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LotPropertyDTO {
    @JsonProperty("address")
    private String address;

    @JsonProperty("area")
    private String area;

    @JsonProperty("kad")
    private String kad;

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lon")
    private String longitude;

    @JsonProperty("Вид объекта недвижимости:")
    private String propertyType;

    @JsonProperty("Вид, номер и дата государственной регистрации права:")
    private String registrationDetails;

    @JsonProperty("Дата внесения кад. стимости:")
    private String cadastralCostDate;

    @JsonProperty("Дата обновления информации:")
    private String informationUpdateDate;

    @JsonProperty("Дата определения кад. стимости:")
    private String cadastralValuationDate;

    @JsonProperty("Дата присвоения кадастрового номера:")
    private String cadastralNumberAssignmentDate;

    @JsonProperty("Кадастровая стоимость (руб):")
    private String cadastralValue;

    @JsonProperty("Назначение:")
    private String purpose;

    @JsonProperty("Ограничение прав и обременение объекта недвижимости:")
    private String encumbrances;

    @JsonProperty("Форма собственности:")
    private String ownershipForm;

    @JsonProperty("Этаж:")
    private String floor;
}
