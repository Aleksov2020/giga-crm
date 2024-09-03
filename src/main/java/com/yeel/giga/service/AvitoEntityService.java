package com.yeel.giga.service;

import com.yeel.giga.model.AvitoEntity;
import com.yeel.giga.repository.AvitoEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvitoEntityService {
    private final AvitoEntityRepository avitoEntityRepository;

    public AvitoEntity updateAvitoEntity(AvitoEntity avitoEntity) {
        if (avitoEntityRepository.existsById(avitoEntity.getId())) {
            return avitoEntityRepository.save(
                    avitoEntity
            );
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
