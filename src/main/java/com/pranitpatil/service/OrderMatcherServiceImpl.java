package com.pranitpatil.service;

import com.pranitpatil.dto.Order;
import com.pranitpatil.dto.OrderType;
import com.pranitpatil.dto.Trade;

import java.util.Optional;

public class OrderMatcherServiceImpl implements OrderMatcherService {

    private OrderBookService orderBookService = OrderBookStorageService.getInstance();
    private TradeBookService tradeBookService = TradeBookStorageService.getInstance();

    private static final OrderMatcherServiceImpl INSTANCE = new OrderMatcherServiceImpl();

    public static OrderMatcherServiceImpl getInstance() {
        return INSTANCE;
    }

    private OrderMatcherServiceImpl() {
    }

    @Override
    public void matchOrder(Order order) {
        boolean isTradeExecuted = false;

        if (OrderType.BUY.equals(order.orderType())) {
            isTradeExecuted = checkBuyOrder(order);
        } else if (OrderType.SELL.equals(order.orderType())) {
            isTradeExecuted = checkSellOrder(order);
        }

        //Check for remaining orders until nothing matches
        while (isTradeExecuted) {
            isTradeExecuted = false;

            if (orderBookService.peekBuyOrder().isPresent()) {
                isTradeExecuted = checkBuyOrder(orderBookService.peekBuyOrder().get());
            } else if (orderBookService.peekSellOrder().isPresent()) {
                isTradeExecuted = checkSellOrder(orderBookService.peekSellOrder().get());
            }
        }
    }

    private boolean checkSellOrder(Order order) {

        Optional<Order> buyOrder = orderBookService.peekBuyOrder();

        if (buyOrder.isPresent() && order.price() <= buyOrder.get().price()) {
            executeTrade(buyOrder.get(), order);
            return true;
        }
        return false;
    }

    private boolean checkBuyOrder(Order order) {

        Optional<Order> sellOrder = orderBookService.peekSellOrder();

        if (sellOrder.isPresent() && order.price() >= sellOrder.get().price()) {
            executeTrade(order, sellOrder.get());
            return true;
        }
        return false;
    }

    @Override
    public void executeTrade(Order buyOrder, Order sellOrder) {
        int quantity = Math.min(buyOrder.quantity(), sellOrder.quantity());

        if (buyOrder.quantity() > quantity) {
            Order modifiedBuyOrder = new Order(buyOrder.orderId(),
                    buyOrder.orderType(),
                    buyOrder.price(),
                    buyOrder.quantity() - quantity, buyOrder.createdAt());
            orderBookService.modifyFirstBuyOrder(modifiedBuyOrder);
        } else {
            orderBookService.removeBuyOrder();
        }

        if (sellOrder.quantity() > quantity) {
            Order modifiedSellOrder = new Order(sellOrder.orderId(),
                    sellOrder.orderType(),
                    sellOrder.price(),
                    sellOrder.quantity() - quantity,
                    sellOrder.createdAt());
            orderBookService.modifyFirstSellOrder(modifiedSellOrder);
        } else {
            orderBookService.removeSellOrder();
        }

        Trade trade = new Trade(buyOrder.orderId(), sellOrder.orderId(), sellOrder.price(), quantity);
        tradeBookService.executeTrade(trade);
    }
}
