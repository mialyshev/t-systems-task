package com.javaschool.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserUpdatePassDto {

    private long id;
    private String oldPassword;
    private String password;
    private String confirmPassword;
}
