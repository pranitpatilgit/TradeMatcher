package com.pranitpatil.dto;

public record Trade(String buyOrderId, String sellOrderId, int price, int quantity) {
}
