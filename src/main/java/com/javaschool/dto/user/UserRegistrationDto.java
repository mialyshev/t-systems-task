package com.javaschool.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRegistrationDto implements Serializable {

    @NotEmpty(message = "First name should not be empty")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    @Size(min = 2, max = 50)
    private String lastName;

    @NotEmpty(message = "Date of birth should not be empty")
    private String dob;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Confirm email should not be empty")
    @Email(message = "email should be valid")
    private String confirmEmail;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotEmpty(message = "Confirm password should not be empty")
    private String confirmPassword;

}
