package com.yeel.giga.dto.request.appRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotDetailDTO {
    private List<LotPropertyDTO> properties;
    private String title;
    private String url;
}
