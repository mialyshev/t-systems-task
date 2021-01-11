package com.javaschool.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private long id;
    private String country;
    private String city;
    private String postalCode;
    private String street;
    private String houseNumber;
    private String apartamentNumber;
}
