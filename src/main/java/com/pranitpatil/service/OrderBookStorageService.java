package com.pranitpatil.service;

import com.pranitpatil.dto.Order;
import com.pranitpatil.storage.OrderBook;
import com.pranitpatil.util.NumberFormatter;

import java.util.Optional;

public class OrderBookStorageService implements OrderBookService {
    
    private OrderBook orderBook;
    private static final OrderBookStorageService INSTANCE = new OrderBookStorageService();
    private static final String PRINT_SEPARATOR = " ";
    private static final String PRINT_PADDING = "                  ";
    private static final String PRINT_DIVIDER = " | ";
    
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

    @Override
    public void resetOrderBook() {
        orderBook.getBuyQueue().clear();
        orderBook.getSellQueue().clear();
    }
    
    @Override
    public String printOrderBook() {
        StringBuilder builder = new StringBuilder();
        
        while (!orderBook.getBuyQueue().isEmpty() || !orderBook.getSellQueue().isEmpty()){
            if (orderBook.getBuyQueue().isEmpty()){
                builder.append(PRINT_PADDING);
            }
            else {
                Order order = removeBuyOrder().get();
                builder.append(NumberFormatter.formatAmountToString(order.price()));
                builder.append(PRINT_SEPARATOR);
                builder.append(NumberFormatter.addPaddingToNumber(order.quantity()));
            }

            builder.append(PRINT_DIVIDER);

            if (orderBook.getSellQueue().isEmpty()){
                builder.append(PRINT_PADDING);
            }
            else {
                Order order = removeSellOrder().get();
                builder.append(NumberFormatter.formatAmountToString(order.price()));
                builder.append(PRINT_SEPARATOR);
                builder.append(NumberFormatter.addPaddingToNumber(order.quantity()));
            }

            builder.append("\n");
        }
        
        return builder.toString();
    }
}
