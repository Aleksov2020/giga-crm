package com.yeel.giga.dto.request.appRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotAppDTO {
    private LotDetailDTO tbankrot;
    private List<AvitoEntityDTO> properties;
}
