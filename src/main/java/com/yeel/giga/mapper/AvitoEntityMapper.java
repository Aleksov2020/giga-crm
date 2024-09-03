package com.yeel.giga.mapper;

import com.yeel.giga.dto.request.appRequest.AvitoEntityDTO;
import com.yeel.giga.dto.request.appRequest.AvitoEntityDetailDTO;
import com.yeel.giga.enums.AvitoEntityStatus;
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
        avitoEntity.setAvitoEntityStatus(AvitoEntityStatus.ACTIVE);

        return avitoEntity;
    }

    public AvitoEntity mapAvitoEntityDetailDTOtoAvitoEntity(AvitoEntityDetailDTO avitoEntityDetailDTO) {
        return new AvitoEntity(
                avitoEntityDetailDTO.getId(),
                avitoEntityDetailDTO.getAdName(),
                avitoEntityDetailDTO.getSquareHouse(),
                avitoEntityDetailDTO.getSquareField(),
                avitoEntityDetailDTO.getPrice(),
                avitoEntityDetailDTO.getPricePerMeter(),
                avitoEntityDetailDTO.getPhone(),
                avitoEntityDetailDTO.getViewsCount(),
                avitoEntityDetailDTO.getAvitoEntityStatus()
        );
    }

}
