package com.example.userservice.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RequestLogin {

    @Email(message = "invalid email")
    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull(message = "password cannot be null")
    @Length(min = 8, message = "password not be less than two characters")
    private String password;

}
