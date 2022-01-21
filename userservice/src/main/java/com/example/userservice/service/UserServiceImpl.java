package com.example.userservice.service;

import com.example.userservice.Repository.UserRepository;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        // DO userDto.setEncrptedPwd();
        UserEntity user = UserEntity.createUser(userDto);
        UserEntity save = userRepository.save(user);

        return null;
    }
}
