package com.pranitpatil.service;

import com.pranitpatil.dto.Order;

import java.util.Optional;

public interface OrderBookService {
    
    Order addBuyOrder(Order order);
    Order addSellOrder(Order order);

    /**
     * Gets first order from Order Buy Book
     */
    Optional<Order> peekBuyOrder();

    /**
     * Gets first order from Order Sell Book
     */
    Optional<Order> peekSellOrder();

    Optional<Order> removeBuyOrder();

    Optional<Order> removeSellOrder();
    
    Order modifyFirstBuyOrder(Order order);
    
    Order modifyFirstSellOrder(Order order);

    /**
     * Resets the order book.
     * Used for tests.
     */
    void resetOrderBook();
}
