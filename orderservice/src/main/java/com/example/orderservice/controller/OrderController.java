package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.RequestOrder;
import com.example.orderservice.dto.ResponseOrder;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {
    public static int JPA = 0;
    public static int KAFKA = 1;
    private final WebServerApplicationContext webServerApplicationContext;
    private final OrderService orderService;
    private final ModelMapper mapper;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;
    @GetMapping("/health-check")
    public String status(){
        return "It's Working in User Service" + webServerApplicationContext.getWebServer().getPort();
    }
    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,@RequestBody RequestOrder orderDetails){

        OrderDto orderDto = mapper.map(orderDetails,OrderDto.class);
        orderDto.setUserId(userId);


        OrderDto createdOrderDto = orderService.createOrder(orderDto,KAFKA);


        kafkaProducer.send("example-catalog-topic",orderDto);
        orderProducer.send("orders",orderDto);

        ResponseOrder responseOrder = mapper.map(createdOrderDto, ResponseOrder.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseOrder);
    }


    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> createOrder(@PathVariable("userId") String userId) {
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = orders.stream()
                .map(orderDto -> mapper.map(orderDto, ResponseOrder.class))
                .collect(toList());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
