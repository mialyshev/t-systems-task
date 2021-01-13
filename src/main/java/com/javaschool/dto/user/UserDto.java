package com.javaschool.dto.user;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
}