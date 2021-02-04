package com.javaschool.dto.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private long id;

    @NotEmpty(message = "Card number must be filled")
    @Size(min = 16, max = 16, message = "The number must be 16 digits")
    @Pattern(regexp = "[0-9]+", message = "Only numbers can be entered")
    private String number;

    private String validatyDate;

    private String owner;

    @NotEmpty(message = "Code of card must be filled")
    @Size(min = 3, max = 3, message = "The code must be 3 digits")
    @Pattern(regexp = "[0-9]+", message = "Only numbers can be entered")
    private String code;
}
