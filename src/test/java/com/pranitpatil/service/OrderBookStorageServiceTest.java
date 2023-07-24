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

    @Test
    void test_whenAllTypeOfOrdersPresent_thenPrintOrderBook() {
        orderBookStorageService.addSellOrder(new Order("1", OrderType.SELL, 100, 1000, LocalDateTime.now()));
        orderBookStorageService.addSellOrder(new Order("2", OrderType.SELL, 98, 1200, LocalDateTime.now()));
        orderBookStorageService.addSellOrder(new Order("3", OrderType.SELL, 123456, 123456789, LocalDateTime.now()));

        orderBookStorageService.addBuyOrder(new Order("4", OrderType.BUY, 101, 1001, LocalDateTime.now()));
        orderBookStorageService.addBuyOrder(new Order("5", OrderType.BUY, 456123, 987654321, LocalDateTime.now()));
        orderBookStorageService.addBuyOrder(new Order("6", OrderType.BUY, 99, 500, LocalDateTime.now()));


        String result = orderBookStorageService.printOrderBook();
        assertEquals("""
                        987,654,321 456123 |     98       1,200
                              1,001    101 |    100       1,000
                                500     99 | 123456 123,456,789 
                        """,
                result);
    }

    @Test
    void test_whenLessSellOrdersArePresent_thenPrintOrderBook() {
        orderBookStorageService.addSellOrder(new Order("1", OrderType.SELL, 100, 1000, LocalDateTime.now()));

        orderBookStorageService.addBuyOrder(new Order("4", OrderType.BUY, 101, 1001, LocalDateTime.now()));
        orderBookStorageService.addBuyOrder(new Order("5", OrderType.BUY, 456123, 987654321, LocalDateTime.now()));
        orderBookStorageService.addBuyOrder(new Order("6", OrderType.BUY, 99, 500, LocalDateTime.now()));


        String result = orderBookStorageService.printOrderBook();
        assertEquals("""
                        987,654,321 456123 |    100       1,000
                              1,001    101 |                  \s
                                500     99 |                  \s
                        """,
                result);
    }

    @Test
    void test_whenOnlyBuyOrdersPresent_thenPrintOrderBook() {
        orderBookStorageService.addBuyOrder(new Order("4", OrderType.BUY, 101, 1001, LocalDateTime.now()));
        orderBookStorageService.addBuyOrder(new Order("5", OrderType.BUY, 654321, 987456123, LocalDateTime.now()));
        orderBookStorageService.addBuyOrder(new Order("6", OrderType.BUY, 99, 500, LocalDateTime.now()));


        String result = orderBookStorageService.printOrderBook();
        assertEquals("""
                        987,456,123 654321 |                  \s
                              1,001    101 |                  \s
                                500     99 |                  \s
                        """,
                result);
    }

    @Test
    void test_whenOnlySellOrdersPresent_thenPrintOrderBook() {
        orderBookStorageService.addSellOrder(new Order("1", OrderType.SELL, 100, 1000, LocalDateTime.now()));
        orderBookStorageService.addSellOrder(new Order("2", OrderType.SELL, 98, 1200, LocalDateTime.now()));
        orderBookStorageService.addSellOrder(new Order("3", OrderType.SELL, 123456, 123456789, LocalDateTime.now()));

        String result = orderBookStorageService.printOrderBook();
        assertEquals("""
                                           |     98       1,200
                                           |    100       1,000
                                           | 123456 123,456,789
                        """,
                result);
    }
}
