package com.employee.management.service;

import com.employee.management.dto.OrderItemDTO;
import com.employee.management.dto.OrderResponse;
import com.employee.management.model.*;
import com.employee.management.repositry.OrderItemRepositry;
import com.employee.management.repositry.OrderRepositry;
import com.employee.management.repositry.StudentRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepositry orderRepositry;
    private final StudentRepositry studentRepositry;
    private final CardService cardService;

    public Optional<OrderResponse> createOrder(String studentId) {
        // Validate the cart
        List<CardItem> cardItem = cardService.getAllCardItems(studentId);
        if(cardItem.isEmpty()){
            return Optional.empty();
        }

        // Validate the User
        Optional<Student> studentOpt = studentRepositry.findById(Long.valueOf(studentId));
        if(studentOpt.isEmpty()){
            return Optional.empty();
        }
        Student student = studentOpt.get();


        // Calculate totalPrice
        BigDecimal totalPrice = cardItem.stream()
                .map(CardItem::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);


        // Create Order
        Order order = new Order();
        order.setStudent(student);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cardItem.stream().map(item -> new OrderItem(
                null,
                item.getProduct(),
                item.getQuantity(),
                item.getPrice(),
                order
        )).toList();
        order.setItems(orderItems);
        Order savedorder = orderRepositry.save(order);


        // Clear the cart
        cardService.clearCard(studentId);
        return Optional.of(maptoOrderResponse(savedorder));
    }

    private OrderResponse maptoOrderResponse(Order savedorder) {
        return new OrderResponse(
          savedorder.getId(),
          savedorder.getTotalAmount(),
                savedorder.getStatus(),
                savedorder.getItems().stream().map(orderItem -> new OrderItemDTO(
                        orderItem.getId(),
                        orderItem.getProduct().getId(),
                        orderItem.getQuantity(),
                        orderItem.getPrice(),
                        orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                )).toList(),
                savedorder.getCreatedAt()
        );
    }

}
