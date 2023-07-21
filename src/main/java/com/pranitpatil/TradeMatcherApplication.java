package com.pranitpatil;

import com.pranitpatil.dto.Order;
import com.pranitpatil.service.ConsoleIOService;

import java.util.List;

public class TradeMatcherApplication {
    
    private static ConsoleIOService consoleIOService = ConsoleIOService.getInstance();

    public static void main(String[] args) {
        System.out.println("Please enter all orders separated by comma, leave the line blank once done.");
        List<Order> orders = consoleIOService.getOrderInput(System.in);
        System.out.println(orders);
    }
}
