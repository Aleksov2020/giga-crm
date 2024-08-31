package com.yeel.giga.mapper;

import com.yeel.giga.dto.request.LotMoneyDTO;
import com.yeel.giga.model.LotMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LotMoneyMapper {
    public LotMoney mapLotMoneyDTOToLotMoney(LotMoneyDTO lotMoneyDTO) {
        return new LotMoney(
                lotMoneyDTO.getId(),
                lotMoneyDTO.getZadatok(),
                lotMoneyDTO.getStavka(),
                lotMoneyDTO.getPrice(),
                lotMoneyDTO.getDateZadatok()
        );
    }
}
