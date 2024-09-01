package com.yeel.giga.controller;

import com.yeel.giga.dto.request.HandLot;
import com.yeel.giga.dto.response.LotBasicDTO;
import com.yeel.giga.mapper.LotMapper;
import com.yeel.giga.model.Lot;
import com.yeel.giga.model.UserData;
import com.yeel.giga.service.LotService;
import com.yeel.giga.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/lot")
public class LotController {
    private final LotService lotService;
    private final LotMapper lotMapper;
    private final UserDataService userDataService;

    @GetMapping()
    public List<LotBasicDTO> getAllBasic() {
        return lotService.getAll().stream().map(
                lotMapper::mapLotToLotBasicDTO
        ).toList();
    }

    @GetMapping("detailed")
    public Lot getLotDetailed(@RequestParam Long id) {
        return lotService.find(id);
    }

    @PostMapping()
    public Lot createLot(@RequestBody HandLot handLot) {
        UserData userData = userDataService.find(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName()
        );

        return lotService.create(
                lotMapper.mapHandLotToLot(handLot, userData)
        );
    }

}
