package com.yeel.giga.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeel.giga.dto.request.appRequest.LotAppDTO;
import com.yeel.giga.mapper.LotMapper;
import com.yeel.giga.model.UserData;
import com.yeel.giga.service.EncryptionService;
import com.yeel.giga.service.LotService;
import com.yeel.giga.service.UserDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/app")
public class AppController {
    private final UserDataService userDataService;
    private final EncryptionService encryptionService;
    private final LotMapper lotMapper;
    private final LotService lotService;

    @PostMapping()
    public ResponseEntity<String> createLot(@RequestParam String key,
                            @RequestParam String username,
                            @RequestBody String data) {
        log.info("Test");
        UserData owner = userDataService.find(username);
        encryptionService.checkPassword(owner.getUsername() + owner.getId(), key);

        ObjectMapper objectMapper = new ObjectMapper();
        LotAppDTO lotAppDTO;
        try {
            lotAppDTO = objectMapper.readValue(data, LotAppDTO.class);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid JSON format");
        }

        lotService.create(
                lotMapper.mapAppLotDTOtoLot(
                        lotAppDTO,
                        owner
                )
        );

        log.info(lotAppDTO.getTbankrot().getTitle());

        return ResponseEntity.ok("Created");
    }
}
