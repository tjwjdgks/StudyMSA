package com.example.userservice.service;

import com.example.userservice.Repository.UserRepository;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.vo.ResponseUser;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncrptedPwd(passwordEncoder.encode(userDto.getPassword()));
        UserEntity user = UserEntity.createUser(userDto);
        UserEntity save = userRepository.save(user);

        return modelMapper.map(save, UserDto.class);
    }

    @Override
    public ResponseUser getUserByUserId(String userId) {
        Optional<UserEntity> byUserId = userRepository.findByUserId(userId);

        if(byUserId.isPresent())
            return modelMapper.map(byUserId.get(),ResponseUser.class);
        ResponseUser responseUser = new ResponseUser();
        responseUser.setErrorMessage("error none match user-id");
        return responseUser;
    }

    @Override
    public List<ResponseUser> getUserByAll() {
        List<UserEntity> all = userRepository.findAll();
        return all.stream().map(i->modelMapper.map(i,ResponseUser.class)).collect(toList());
    }
}
