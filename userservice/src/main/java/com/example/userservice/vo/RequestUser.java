package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @Email(message = "Email invalid")
    private String email;

    @NotNull(message = "name not be null")
    @Size(min=2,message = "name not be less than two characters")
    private String name;


    @NotNull(message = "password not be null")
    @Size(min=8,message = "password must be equal or grater than 8 characters")
    private String password;

}
