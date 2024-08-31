package com.yeel.giga.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserSettings {
    @Id
    @GeneratedValue
    private Long id;
    private Boolean isShowName;
    private Boolean isShowDateEnd;
    private Boolean isShowZadatok;
    private Boolean isShowPrice;
    private Boolean isShowDateZadatok;
    private Boolean isShowLotType;
    private Boolean isShowLotStatus;
    private Boolean isShowComment;
    private Boolean isShowPinnedLot;
    private Boolean isShowDateCreated;
}
