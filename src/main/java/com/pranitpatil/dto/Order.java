package com.pranitpatil.dto;

public record Order(String orderId, OrderType orderType, int price, int quantity) {
}
