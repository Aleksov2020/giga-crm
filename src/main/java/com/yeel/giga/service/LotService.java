package com.yeel.giga.service;

import com.yeel.giga.model.UserData;
import com.yeel.giga.repository.LotRepository;
import com.yeel.giga.model.Lot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LotService {
    private final LotRepository lotRepository;

    public List<Lot> getAll() {
        return lotRepository.findAll();
    }

    public List<Lot> getLotByOwner(UserData owner) {
        return lotRepository.findLotsByOwnerIs(owner);
    }

    public Lot find(Long id) {
        return lotRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    public Lot create(Lot lot) {
        return lotRepository.save(
                lot
        );
    }

    public Lot update(Lot lot) {
        log.info(lot.getId() + " - id");

        if (lotRepository.existsById(lot.getId())) {
            return lotRepository.save(lot);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
