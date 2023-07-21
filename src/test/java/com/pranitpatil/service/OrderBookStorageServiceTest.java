package com.pranitpatil.service;

import com.pranitpatil.dto.Order;
import com.pranitpatil.dto.OrderType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderBookStorageServiceTest {
    
    private OrderBookStorageService orderBookStorageService;

    @BeforeAll
    void init() {
        orderBookStorageService = orderBookStorageService.getInstance();
    }

    @Test
    void test_whenAddBuyOrder_thenAllOrdersArePresentInCorrectOrder() {
        orderBookStorageService.addBuyOrder(new Order("1", OrderType.BUY, 99, 1000, LocalDateTime.now()));
        orderBookStorageService.addBuyOrder(new Order("2", OrderType.BUY, 98, 1200, LocalDateTime.now()));
        orderBookStorageService.addBuyOrder(new Order("3", OrderType.BUY, 99, 500, LocalDateTime.now()));

        assertEquals("1", orderBookStorageService.removeBuyOrder().get().orderId());
        assertEquals("3", orderBookStorageService.removeBuyOrder().get().orderId());
        assertEquals("2", orderBookStorageService.removeBuyOrder().get().orderId());
    }

    @Test
    void test_whenAddSellOrder_thenAllOrdersArePresentInCorrectOrder() {
        orderBookStorageService.addSellOrder(new Order("1", OrderType.SELL, 100, 1000, LocalDateTime.now()));
        orderBookStorageService.addSellOrder(new Order("2", OrderType.SELL, 98, 1200, LocalDateTime.now()));
        orderBookStorageService.addSellOrder(new Order("3", OrderType.SELL, 99, 500, LocalDateTime.now()));

        assertEquals("2", orderBookStorageService.removeSellOrder().get().orderId());
        assertEquals("3", orderBookStorageService.removeSellOrder().get().orderId());
        assertEquals("1", orderBookStorageService.removeSellOrder().get().orderId());
    }
}
