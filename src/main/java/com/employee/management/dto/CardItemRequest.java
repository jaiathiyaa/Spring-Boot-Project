package com.employee.management.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardItemRequest {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
