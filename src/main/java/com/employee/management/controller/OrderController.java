package com.employee.management.controller;

import com.employee.management.dto.OrderResponse;
import com.employee.management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-User-ID") String studentId){
        return orderService.createOrder(studentId).map(orderResponse -> new ResponseEntity<>(orderResponse, HttpStatus.CREATED)).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
