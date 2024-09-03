package com.yeel.giga.mapper;

import com.yeel.giga.dto.request.HandLot;
import com.yeel.giga.dto.request.appRequest.LotAppDTO;
import com.yeel.giga.dto.response.LotBasicDTO;
import com.yeel.giga.enums.LotStatus;
import com.yeel.giga.model.BuyInformation;
import com.yeel.giga.model.Lot;
import com.yeel.giga.model.LotMoney;
import com.yeel.giga.model.UserData;
import com.yeel.giga.repository.AvitoEntityRepository;
import com.yeel.giga.repository.BuyInformationRepository;
import com.yeel.giga.repository.LotMoneyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotMapper {
    private final LotMoneyMapper lotMoneyMapper;
    private final BuyInformationMapper buyInformationMapper;
    private final BuyInformationRepository buyInformationRepository;
    private final LotMoneyRepository lotMoneyRepository;
    private final AvitoEntityRepository avitoEntityRepository;
    private final AvitoEntityMapper avitoEntityMapper;


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

    public Lot mapHandLotToLot(HandLot handLot, UserData owner) {
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
                                handLot.getLotMoney()
                        )
                ),
                buyInformationRepository.save(
                        buyInformationMapper.mapBuyInformationDTOToBuyInformation(
                                handLot.getBuyInformation()
                        )
                ),
                new ArrayList<>(),
                owner
        );
    }

    public Lot mapHandLotToLotForUpdate(HandLot handLot, UserData owner) {
        log.info(handLot.getLotLink());

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
                                handLot.getLotMoney()
                        )
                ),
                buyInformationRepository.save(
                        buyInformationMapper.mapBuyInformationDTOToBuyInformation(
                                handLot.getBuyInformation()
                        )
                ),
                handLot.getAvitoEntities(),
                owner
        );
    }

    public Lot mapAppLotDTOtoLot(LotAppDTO lotAppDTO, UserData owner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Lot lot = new Lot();
        LotMoney lotMoney = new LotMoney();
        BuyInformation buyInformation = new BuyInformation();

        lot.setName(lotAppDTO.getTbankrot().getTitle());
        lot.setAddress(lotAppDTO.getTbankrot().getProperties().get(0).getAddress());

        lot.setKadastrNumber(lotAppDTO.getTbankrot().getProperties().get(0).getKad());
        lot.setKadastrPrice(lotAppDTO.getTbankrot().getProperties().get(0).getCadastralValue());

        lot.setNumberSell(lotAppDTO.getTbankrot().getProperties().get(0).getRegistrationDetails());
        lot.setSquare(lotAppDTO.getTbankrot().getProperties().get(0).getArea());
        lot.setLotStatus(LotStatus.REGISTERED);

        lot.setDatePublished(LocalDate.parse(lotAppDTO.getTbankrot().getProperties().get(0).getInformationUpdateDate(), formatter));
        lot.setDateCreated(LocalDate.now());
        lot.setLotLink(lotAppDTO.getTbankrot().getUrl());

        lot.setLotMoney(lotMoneyRepository.save(
                lotMoney
        ));

        lot.setBuyInformation(buyInformationRepository.save(
                buyInformation
        ));

        lot.setOwner(owner);

        lot.setAvitoEntities(
                avitoEntityRepository.saveAll(
                        lotAppDTO.getProperties().stream().map(
                                avitoEntityMapper::mapAvitoEntityDTOtoAvitoEntity
                        ).toList()
                )
        );

        return lot;
    }
}
