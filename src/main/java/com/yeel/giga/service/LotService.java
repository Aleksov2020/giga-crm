package com.yeel.giga.service;

import com.yeel.giga.model.UserData;
import com.yeel.giga.repository.LotRepository;
import com.yeel.giga.model.Lot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LotService {
    private final LotRepository lotRepository;

    public List<Lot> getAll() {
        return lotRepository.findAll();
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
}
