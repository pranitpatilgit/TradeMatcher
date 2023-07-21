package com.pranitpatil.service;

import com.pranitpatil.dto.Order;
import com.pranitpatil.dto.OrderType;
import com.pranitpatil.exception.TradeMatcherException;

import java.time.LocalDateTime;

public class OrderMapper {
    
    private static OrderMapper INSTANCE = new OrderMapper();
    private static String SEPARATOR = ",";

    private OrderMapper() {
    }

    public static OrderMapper getInstance() {
        return INSTANCE;
    }

    public Order mapOrderFromCSV(String line){
        String[] tokens = line.split(SEPARATOR);

        try {
            return new Order(tokens[0].trim(),
                    OrderType.fromValue(tokens[1].trim()),
                    Integer.parseInt(tokens[2].trim()),
                    Integer.parseInt(tokens[3].trim()),
                    LocalDateTime.now());
        } catch (Exception e) {
            throw new TradeMatcherException("Exception while mapping input to DTO", e);
        }
    }
}
