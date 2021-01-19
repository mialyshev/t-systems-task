package com.javaschool.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {
    @Size(min = 1, max = 100, message = "The size must be in the range 2 to 100")
    private String brandName;
}
