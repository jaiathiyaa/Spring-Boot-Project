package com.employee.management.repositry;

import com.employee.management.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepositry extends JpaRepository<OrderItem,Long> {
}
