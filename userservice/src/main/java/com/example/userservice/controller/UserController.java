package com.example.userservice.controller;


import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final Environment environment;
    private final ServletWebServerApplicationContext webServerAppCxt;
    @Value("${greeting.message}")
    public String welcomeMessage;

    @GetMapping("/health-check")
    public String status(){
        return String.format("it's woring in user service"
                + " port " + webServerAppCxt.getWebServer().getPort()
                + " token secret " + environment.getProperty("token.secret")
                + " token expire " + environment.getProperty("token.expiration_time"));


    }

    @GetMapping("/welcome")
    public String welcome(){
        return welcomeMessage;
    }
   
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);

        ResponseUser responseUser = modelMapper.map(createdUser, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
    
    @GetMapping("/users")
    public ResponseEntity getUser(){

        List<ResponseUser> userByAll = userService.getUserByAll();

        return ResponseEntity.status(HttpStatus.OK).body(userByAll);
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") String userId){

        ResponseUser userByUserId = userService.getUserByUserId(userId);
        return  ResponseEntity.status(HttpStatus.OK).body(userByUserId);
    }

}
