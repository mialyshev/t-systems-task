package com.javaschool.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private long id;

    @NotNull
    @Min(value = 1, message = "Quantity must be greater than 1")
    private int quantity;

    @NotNull
    private int price;

    @Size(min = 2, max = 100, message = "The model must be in the range 2 to 100")
    private String model;

    @NotEmpty(message = "url must be entered")
    private String url;

    @NotEmpty(message = "category must be entered")
    private String categoryName;

    @NotEmpty(message = "brand must be entered")
    private String brandName;

    @NotEmpty(message = "season must be entered")
    private String seasonName;

    @NotEmpty(message = "color must be entered")
    private String colorName;

    @NotEmpty(message = "material must be entered")
    private String materialName;

    @NotNull
    private float size;
}
