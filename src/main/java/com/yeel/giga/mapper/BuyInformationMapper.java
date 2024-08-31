package com.yeel.giga.mapper;

import com.yeel.giga.dto.request.BuyInformationDTO;
import com.yeel.giga.model.BuyInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuyInformationMapper {
    public BuyInformation mapBuyInformationDTOToBuyInformation(BuyInformationDTO buyInformationDTO) {
        return new BuyInformation(
                buyInformationDTO.getId(),
                buyInformationDTO.getBuyPrice(),
                buyInformationDTO.getSellPrice(),
                buyInformationDTO.getResult()
        );
    }
}
