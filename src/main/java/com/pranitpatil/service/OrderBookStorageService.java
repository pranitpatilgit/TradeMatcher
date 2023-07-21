package com.pranitpatil.service;

import com.pranitpatil.dto.Order;
import com.pranitpatil.storage.OrderBook;

import java.util.Optional;

public class OrderBookStorageService implements OrderBookService {
    
    private OrderBook orderBook;
    private static final OrderBookStorageService INSTANCE = new OrderBookStorageService();
    private OrderBookStorageService() {
        this.orderBook = new OrderBook();
    }

    public static OrderBookStorageService getInstance() {
        return INSTANCE;
    }

    @Override
    public Order addBuyOrder(Order order) {
        orderBook.getBuyQueue().add(order);
        return order;
    }

    @Override
    public Order addSellOrder(Order order) {
        orderBook.getSellQueue().add(order);
        return order;
    }

    @Override
    public Optional<Order> peekBuyOrder() {
        return orderBook.getBuyQueue().isEmpty() 
                ? Optional.empty() 
                : Optional.of(orderBook.getBuyQueue().peek());
    }

    @Override
    public Optional<Order> peekSellOrder() {
        return orderBook.getSellQueue().isEmpty()
                ? Optional.empty()
                : Optional.of(orderBook.getSellQueue().peek());
    }

    @Override
    public Optional<Order> removeBuyOrder() {
        return orderBook.getBuyQueue().isEmpty()
                ? Optional.empty()
                : Optional.of(orderBook.getBuyQueue().poll());
    }

    @Override
    public Optional<Order> removeSellOrder() {
        return orderBook.getSellQueue().isEmpty()
                ? Optional.empty()
                : Optional.of(orderBook.getSellQueue().poll());
    }

    @Override
    public Order modifyFirstBuyOrder(Order order) {
        orderBook.getBuyQueue().poll();
        orderBook.getBuyQueue().add(order);
        return order;
    }

    @Override
    public Order modifyFirstSellOrder(Order order) {
        orderBook.getSellQueue().poll();
        orderBook.getSellQueue().add(order);
        return order;
    }
}
