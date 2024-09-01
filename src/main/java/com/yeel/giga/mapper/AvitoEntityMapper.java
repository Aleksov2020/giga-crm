package com.yeel.giga.mapper;

import com.yeel.giga.dto.request.appRequest.AvitoEntityDTO;
import com.yeel.giga.model.AvitoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AvitoEntityMapper {
    public AvitoEntity mapAvitoEntityDTOtoAvitoEntity(AvitoEntityDTO avitoEntityDTO) {
        AvitoEntity avitoEntity = new AvitoEntity();

        avitoEntity.setLink(avitoEntityDTO.getUrl());
        avitoEntity.setSquareHome(avitoEntityDTO.getData().getSquareHouse());
        avitoEntity.setPrice(avitoEntityDTO.getData().getPrice());
        avitoEntity.setPricePerSquare(avitoEntityDTO.getData().getPricePerMeter());
        avitoEntity.setScope(avitoEntityDTO.getData().getViewsCount());

        return avitoEntity;
    }

}
