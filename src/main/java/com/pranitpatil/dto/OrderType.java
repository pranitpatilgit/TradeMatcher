package com.pranitpatil.dto;

import java.util.HashMap;
import java.util.Map;

public enum OrderType {
    BUY("B"),
    SELL("S");

    private String value;
    private static final Map<String, OrderType> VALUES = new HashMap<>();

    static {
        for (OrderType order : values()) {
            VALUES.put(order.getValue(), order);
        }
    }

    OrderType(String value) {
        this.value = value;
    }

    public static OrderType fromValue(String value) {
        if (VALUES.containsKey(value)) {
            return VALUES.get(value);
        }
        throw new IllegalArgumentException(String.format("Value %s is not found in enum OrderType", value));
    }

    public String getValue() {
        return value;
    }
}
