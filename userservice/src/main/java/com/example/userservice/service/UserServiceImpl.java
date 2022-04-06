package com.example.userservice.service;

import com.example.userservice.Repository.UserRepository;
import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.vo.ResponseOrder;
import com.example.userservice.vo.ResponseUser;
import feign.FeignException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;
    // for rest template
    // private final RestTemplate restTemplate;
    private final OrderServiceClient orderServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if(userEntity == null){
            throw new UsernameNotFoundException(username);
        }
        return new User(userEntity.getEmail(),userEntity.getEncryptedPwd(),new ArrayList<>());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPwd(passwordEncoder.encode(userDto.getPassword()));
        UserEntity user = UserEntity.createUser(userDto);
        UserEntity save = userRepository.save(user);

        return modelMapper.map(save, UserDto.class);
    }

    @Override
    public ResponseUser getUserByUserId(String userId) {
        Optional<UserEntity> byUserId = userRepository.findByUserId(userId);

        if(!byUserId.isPresent()){
            throw  new UsernameNotFoundException("user not found");
        }
        ResponseUser responseUser = modelMapper.map(byUserId.get(), ResponseUser.class);

        /*
        // Using rest template
        // 단점 불편, 장점 서비스 호출이 명확하다
        String orderUrl = String.format(env.getProperty("order_service.url"),userId);
        ResponseEntity<List<ResponseOrder>> orderListResponse =
                restTemplate.exchange(orderUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ResponseOrder>>(){});
        responseUser.setOrders(orderListResponse.getBody());
        */

        // Using a feign client
        // 장점 편리, 단점 서비스 호출하는 것이 명확하지 않다.
        /*
        // fegin exception handling

        List<ResponseOrder> orderList = null;
        try{
            orderList = orderServiceClient.getOrders(userId);
        }catch (FeignException e){
            log.error(e.getMessage());
        }
        */
        // feign error decoder add circuit
        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
        List<ResponseOrder> orderList = circuitbreaker.run(() -> orderServiceClient.getOrders(userId),
                throwable -> new ArrayList<>());
        responseUser.setOrders(orderList);
        return responseUser;
    }

    @Override
    public List<ResponseUser> getUserByAll() {
        List<UserEntity> all = userRepository.findAll();
        return all.stream().map(i->modelMapper.map(i,ResponseUser.class)).collect(toList());
    }

    @Override
    public UserDto getUserDetailsByEmail(String userName) {
        UserEntity byEmail = userRepository.findByEmail(userName);
        if(byEmail == null)
            throw new UsernameNotFoundException("user 정보가 없습니다");
        return modelMapper.map(byEmail, UserDto.class);
    }

}
