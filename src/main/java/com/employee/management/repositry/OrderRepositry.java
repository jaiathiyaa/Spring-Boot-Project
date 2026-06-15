package com.employee.management.repositry;

import com.employee.management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositry extends JpaRepository<Order, Long> {
}
