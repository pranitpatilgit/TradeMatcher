package com.pranitpatil.service.it;

import com.pranitpatil.dto.Order;
import com.pranitpatil.dto.OrderType;
import com.pranitpatil.service.OrderBookStorageService;
import com.pranitpatil.service.OrderMatcherServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderMatcherServiceImplIntegrationTest {

    private OrderMatcherServiceImpl orderMatcherService;
    private OrderBookStorageService orderBookStorageService;

    @BeforeAll
    void init() {
        orderMatcherService = OrderMatcherServiceImpl.getINSTANCE();
        orderBookStorageService = OrderBookStorageService.getInstance();
    }
    
    @BeforeEach
    void reset(){
        orderBookStorageService.resetOrderBook();
    }
    
    @Test
    void test_whenForBuyOrderAndSellOrderExistsWithSameQuantity_thenBothOrdersAreExecutedAndRemoved(){
        orderBookStorageService.addSellOrder(new Order("1", OrderType.SELL, 100, 1000));
        orderBookStorageService.addBuyOrder(new Order("2", OrderType.BUY, 101, 1000));
      
        orderMatcherService.matchOrder(orderBookStorageService.peekBuyOrder().get());
        
        assertTrue(orderBookStorageService.peekBuyOrder().isEmpty());
        assertTrue(orderBookStorageService.peekSellOrder().isEmpty());
    }

    @Test
    void test_whenForSellOrderAndSellOrderExistsWithSameQuantity_thenBothOrdersAreExecutedAndRemoved(){
        orderBookStorageService.addSellOrder(new Order("1", OrderType.SELL, 100, 1000));
        orderBookStorageService.addBuyOrder(new Order("2", OrderType.BUY, 101, 1000));

        orderMatcherService.matchOrder(orderBookStorageService.peekSellOrder().get());

        assertTrue(orderBookStorageService.peekBuyOrder().isEmpty());
        assertTrue(orderBookStorageService.peekSellOrder().isEmpty());
    }

    @Test
    void test_whenForBuyOrderAndSellOrderExistsWithMoreQuantity_thenBuyOrderIsRemovedAndSellOrderIsModified(){
        orderBookStorageService.addSellOrder(new Order("1", OrderType.SELL, 100, 1100));
        orderBookStorageService.addBuyOrder(new Order("2", OrderType.BUY, 101, 1000));

        orderMatcherService.matchOrder(orderBookStorageService.peekBuyOrder().get());

        assertTrue(orderBookStorageService.peekBuyOrder().isEmpty());
        assertEquals(100, orderBookStorageService.peekSellOrder().get().quantity());
    }

    @Test
    void test_whenForBuyOrderAndSellOrderExistsWithLessQuantity_thenSellOrderIsRemovedAndBuyOrderIsModified(){
        orderBookStorageService.addSellOrder(new Order("1", OrderType.SELL, 100, 900));
        orderBookStorageService.addBuyOrder(new Order("2", OrderType.BUY, 101, 1000));

        orderMatcherService.matchOrder(orderBookStorageService.peekBuyOrder().get());

        assertTrue(orderBookStorageService.peekSellOrder().isEmpty());
        assertEquals(100, orderBookStorageService.peekBuyOrder().get().quantity());
    }

    @Test
    void test_whenForSellOrderAndBuyOrderExistsWithLessQuantity_thenBuyOrderIsRemovedAndSellOrderIsModified(){
        orderBookStorageService.addBuyOrder(new Order("1", OrderType.BUY, 101, 900));
        orderBookStorageService.addSellOrder(new Order("2", OrderType.SELL, 100, 950));
        
        orderMatcherService.matchOrder(orderBookStorageService.peekSellOrder().get());

        assertTrue(orderBookStorageService.peekBuyOrder().isEmpty());
        assertEquals(50, orderBookStorageService.peekSellOrder().get().quantity());
    }

    @Test
    void test_whenForSellOrderAndBuyOrderExistsWithMoreQuantity_thenSellOrderIsRemovedAndBuyOrderIsModified(){
        orderBookStorageService.addBuyOrder(new Order("1", OrderType.BUY, 101, 1000));
        orderBookStorageService.addSellOrder(new Order("2", OrderType.SELL, 100, 950));
        
        orderMatcherService.matchOrder(orderBookStorageService.peekSellOrder().get());

        assertTrue(orderBookStorageService.peekSellOrder().isEmpty());
        assertEquals(50, orderBookStorageService.peekBuyOrder().get().quantity());
    }

    @Test
    void test_whenForSellOrderNoBuyOrdersExist_thenSellOrderRemainsTheSame(){
        orderBookStorageService.addSellOrder(new Order("1", OrderType.SELL, 100, 950));

        orderMatcherService.matchOrder(orderBookStorageService.peekSellOrder().get());
        
        assertEquals(950, orderBookStorageService.peekSellOrder().get().quantity());
        assertEquals("1", orderBookStorageService.peekSellOrder().get().orderId());
    }

    @Test
    void test_whenForSellOrderNoMatchingBuyOrdersExist_thenBothOrdersRemainTheSame(){
        orderBookStorageService.addBuyOrder(new Order("1", OrderType.BUY, 99, 1000));
        orderBookStorageService.addSellOrder(new Order("2", OrderType.SELL, 100, 950));

        orderMatcherService.matchOrder(orderBookStorageService.peekSellOrder().get());

        assertEquals(950, orderBookStorageService.peekSellOrder().get().quantity());
        assertEquals("2", orderBookStorageService.peekSellOrder().get().orderId());
        assertEquals(1000, orderBookStorageService.peekBuyOrder().get().quantity());
        assertEquals("1", orderBookStorageService.peekBuyOrder().get().orderId());
    }

    @Test
    void test_whenForBuyOrderNoSellOrdersExist_thenBuyOrderRemainsTheSame(){
        orderBookStorageService.addBuyOrder(new Order("1", OrderType.BUY, 99, 1000));

        orderMatcherService.matchOrder(orderBookStorageService.peekBuyOrder().get());

        assertEquals(1000, orderBookStorageService.peekBuyOrder().get().quantity());
        assertEquals("1", orderBookStorageService.peekBuyOrder().get().orderId());
    }

    @Test
    void test_whenForBuyOrderNoMatchingSellOrdersExist_thenBothOrdersRemainTheSame(){
        orderBookStorageService.addBuyOrder(new Order("1", OrderType.BUY, 99, 1000));
        orderBookStorageService.addSellOrder(new Order("2", OrderType.SELL, 100, 950));

        orderMatcherService.matchOrder(orderBookStorageService.peekBuyOrder().get());

        assertEquals(950, orderBookStorageService.peekSellOrder().get().quantity());
        assertEquals("2", orderBookStorageService.peekSellOrder().get().orderId());
        assertEquals(1000, orderBookStorageService.peekBuyOrder().get().quantity());
        assertEquals("1", orderBookStorageService.peekBuyOrder().get().orderId());
    }
}
