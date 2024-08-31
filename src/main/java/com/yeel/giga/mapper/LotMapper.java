package com.yeel.giga.mapper;

import com.yeel.giga.dto.request.HandLot;
import com.yeel.giga.dto.response.LotBasicDTO;
import com.yeel.giga.model.Lot;
import com.yeel.giga.repository.BuyInformationRepository;
import com.yeel.giga.repository.LotMoneyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class LotMapper {
    private final LotMoneyMapper lotMoneyMapper;
    private final BuyInformationMapper buyInformationMapper;
    private final BuyInformationRepository buyInformationRepository;
    private final LotMoneyRepository lotMoneyRepository;


    public LotBasicDTO mapLotToLotBasicDTO(Lot lot) {
        return new LotBasicDTO(
                lot.getId(),
                lot.getName(),
                lot.getAddress(),
                lot.getDateEnd(),
                lot.getDateCreated(),
                lot.getLotMoney().getZadatok(),
                lot.getLotMoney().getDateZadatok(),
                lot.getLotType(),
                lot.getLotStatus(),
                lot.getComment(),
                lot.getBuyInformation().getResult(),
                lot.getLotMoney().getPrice()
        );
    }

    public Lot mapHandLotToLot(HandLot handLot) {
        return new Lot(
                handLot.getId(),
                handLot.getName(),
                handLot.getAddress(),
                handLot.getPhone(),
                handLot.getEmail(),
                handLot.getKadastrNumber(),
                handLot.getKadastrPrice(),
                handLot.getNumberSell(),
                handLot.getSquare(),
                handLot.getLotType(),
                handLot.getLotStatus(),
                handLot.getDateEnd(),
                handLot.getDatePublished(),
                LocalDate.now(),
                handLot.getLotLink(),
                handLot.getLotDiskLink(),
                handLot.getAvitoLink(),
                handLot.getComment(),
                lotMoneyRepository.save(
                        lotMoneyMapper.mapLotMoneyDTOToLotMoney(
                                handLot.getLotMoneyDTO()
                        )
                ),
                buyInformationRepository.save(
                        buyInformationMapper.mapBuyInformationDTOToBuyInformation(
                                handLot.getBuyInformationDTO()
                        )
                ),
                new ArrayList<>()
        );
    }
}
