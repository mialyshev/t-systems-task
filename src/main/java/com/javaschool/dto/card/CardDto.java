package com.javaschool.dto.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private String number;
    private LocalDate validatyDate;
    private String owner;
    private String code;
}
