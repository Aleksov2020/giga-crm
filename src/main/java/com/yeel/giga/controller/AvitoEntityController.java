package com.yeel.giga.controller;

import com.yeel.giga.dto.request.appRequest.AvitoEntityDetailDTO;
import com.yeel.giga.mapper.AvitoEntityMapper;
import com.yeel.giga.model.AvitoEntity;
import com.yeel.giga.service.AvitoEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/avito")
public class AvitoEntityController {
    private final AvitoEntityService avitoEntityService;
    private final AvitoEntityMapper avitoEntityMapper;

    @PostMapping("update")
    public AvitoEntity updateAvitoEntity(@RequestBody AvitoEntityDetailDTO avitoEntityDetailDTO) {
        return avitoEntityService.updateAvitoEntity(
                avitoEntityMapper.mapAvitoEntityDetailDTOtoAvitoEntity(
                        avitoEntityDetailDTO
                )
        );
    }

}
