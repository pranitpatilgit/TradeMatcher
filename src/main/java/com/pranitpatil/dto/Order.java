package com.pranitpatil.dto;

import java.time.LocalDateTime;

public record Order(String orderId, OrderType orderType, int price, int quantity, LocalDateTime createdAt) {
}
