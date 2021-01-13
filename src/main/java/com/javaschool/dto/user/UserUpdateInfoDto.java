package com.javaschool.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserUpdateInfoDto {

    private long id;
    private String firstName;
    private String lastName;
    private String dob;
    private String email;
    private String confirmEmail;

}
