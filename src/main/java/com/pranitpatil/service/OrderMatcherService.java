package com.pranitpatil.service;

import com.pranitpatil.dto.Order;

public interface OrderMatcherService {
    /**
     * Matches the incoming order against existing orders in order book.
     * Updates the order book by executing the trade.
     * Performs the matching task until there are no possible matches.
     * @param order
     */
    void matchOrder(Order order);

    /**
     * Executes the trade and updates order book.
     * @param buyOrder
     * @param sellOrder
     */
    void executeTrade(Order buyOrder, Order sellOrder);
}
