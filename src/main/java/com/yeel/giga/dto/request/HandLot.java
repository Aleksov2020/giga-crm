package com.yeel.giga.dto.request;

import com.yeel.giga.enums.LotStatus;
import com.yeel.giga.enums.LotType;
import com.yeel.giga.model.AvitoEntity;
import com.yeel.giga.model.BuyInformation;
import com.yeel.giga.model.LotMoney;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandLot {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String kadastrNumber;
    private String kadastrPrice;
    private String numberSell;
    private String square;
    private LotType lotType;
    private LotStatus lotStatus;
    private LocalDate dateEnd;
    private LocalDate datePublished;
    private LocalDate dateCreated;
    private String lotLink;
    private String lotDiskLink;
    private String avitoLink;
    private String comment;
    private LotMoneyDTO lotMoney;
    private BuyInformationDTO buyInformation;
    private List<AvitoEntity> avitoEntities;
}
