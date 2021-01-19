package com.javaschool.dto.order;

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
public class AddressAdditionDto {

    @Size(min = 2, max = 100, message = "The size must be in the range 2 to 100")
    private String country;

    @Size(min = 2, max = 100, message = "The size must be in the range 2 to 100")
    private String city;

    @Size(min = 2, max = 10, message = "The size must be in the range 2 to 10")
    private String postalCode;

    @Size(min = 2, max = 100, message = "The size must be in the range 2 to 100")
    private String street;

    @Size(min = 1, max = 3, message = "The size must be in the range 1 to 3")
    private String houseNumber;

    @Size(min = 1, max = 5, message = "The size must be in the range 1 to 5")
    private String apartamentNumber;

    private boolean isSaved;
}
