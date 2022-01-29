package com.example.orderservice.repository;

import com.example.orderservice.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {

    Order findByOrderId(String orderId);
    List<Order> findByUserId(String userId);
}
