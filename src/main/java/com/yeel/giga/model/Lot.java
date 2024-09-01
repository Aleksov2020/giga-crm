package com.yeel.giga.model;

import com.yeel.giga.enums.LotStatus;
import com.yeel.giga.enums.LotType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String kadastrNumber;
    private String kadastrPrice;
    private String numberSell;
    private String square;
    @Enumerated
    private LotType lotType;
    @Enumerated
    private LotStatus lotStatus;
    private LocalDate dateEnd;
    private LocalDate datePublished;
    private LocalDate dateCreated;
    private String lotLink;
    private String lotDiskLink;
    private String avitoLink;
    private String comment;
    @OneToOne
    private LotMoney lotMoney;
    @OneToOne
    private BuyInformation buyInformation;
    @ManyToMany
    private List<AvitoEntity> avitoEntities;
    @ManyToOne
    private UserData owner;

}
