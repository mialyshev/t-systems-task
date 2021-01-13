package com.javaschool.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private long id;
    private int quantity;
    private int price;
    private String model;
    private String url;
    private String categoryName;
    private String brandName;
    private String seasonName;
    private String colorName;
    private String materialName;
    private float size;
}
