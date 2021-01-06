package com.javaschool.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
}