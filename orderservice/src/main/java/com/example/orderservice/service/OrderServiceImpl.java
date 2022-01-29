package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    @Override
    public OrderDto createOrder(OrderDto orderDetails) {
        orderDetails.setOrderId(UUID.randomUUID().toString());
        orderDetails.setTotalPrice(orderDetails.getQty()* orderDetails.getUnitPrice()); // 수량 * unit price

        Order order = mapper.map(orderDetails, Order.class);
        Order save = orderRepository.save(order);
        return mapper.map(order,OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        Order byOrderId = orderRepository.findByOrderId(orderId);
        return mapper.map(byOrderId,OrderDto.class);
    }

    @Override
    public List<OrderDto> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(order->mapper.map(order,OrderDto.class))
                .collect(toList());
    }
}
