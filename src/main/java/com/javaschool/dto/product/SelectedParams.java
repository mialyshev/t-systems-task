package com.javaschool.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectedParams {
    private String category = null;
    private String brand = null;
    private String color = null;
    private String material = null;
    private String season = null;
}
