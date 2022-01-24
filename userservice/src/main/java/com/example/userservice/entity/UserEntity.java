package com.example.userservice.entity;

import com.example.userservice.dto.UserDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = "id")
public class UserEntity {
    @Id @GeneratedValue
    private Long id;
    private String email;
    private String name;

    @Column(unique = true)
    private String userId;
    private String encryptedPwd;
    private LocalDateTime createdAt;

    private UserEntity(String email, String name, String userId, String encryptedPwd, LocalDateTime createdAt) {
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.encryptedPwd = encryptedPwd;
        this.createdAt = createdAt;
    }

    public static UserEntity createUser(UserDto userDto){
        return new UserEntity(userDto.getEmail(), userDto.getName(),userDto.getUserId(), userDto.getEncrptedPwd(), LocalDateTime.now());
    }
}
