package com.employee.management.service;

import com.employee.management.dto.OrderResponse;
import com.employee.management.model.Order;
import com.employee.management.model.OrderItem;
import com.employee.management.model.Student;
import com.employee.management.repositry.OrderItemRepositry;
import com.employee.management.repositry.OrderRepositry;
import com.employee.management.repositry.StudentRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.FileChannel;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepositry orderRepositry;
    private final StudentRepositry studentRepositry;
    private final OrderItemRepositry orderItemRepositry;
    public Optional<OrderResponse> createOrder(String studentId) {
        return ;
    }
}
