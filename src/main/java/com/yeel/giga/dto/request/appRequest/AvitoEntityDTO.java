package com.yeel.giga.dto.request.appRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvitoEntityDTO {
    private String url;
    private AvitoEntityDetailDTO data;
}
