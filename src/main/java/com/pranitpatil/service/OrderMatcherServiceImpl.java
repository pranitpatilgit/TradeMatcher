package com.pranitpatil.service;

import com.pranitpatil.dto.Order;
import com.pranitpatil.dto.OrderType;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class OrderMatcherServiceImpl implements OrderMatcherService{

    private OrderBookService orderBookService = OrderBookStorageService.getInstance();
    
    private static final OrderMatcherServiceImpl INSTANCE = new OrderMatcherServiceImpl();

    public static OrderMatcherServiceImpl getINSTANCE() {
        return INSTANCE;
    }

    private OrderMatcherServiceImpl() {
    }

    @Override
    public void matchOrder(Order order) {
        checkBuyOrder(order);
        checkSellOrder(order);
        
        //TODO : Recursively check for remaining orders
    }

    private void checkSellOrder(Order order) {
        if(OrderType.SELL.equals(order.orderType())){
            Optional<Order> buyOrder = orderBookService.peekBuyOrder();

            if(buyOrder.isPresent() && order.price() <= buyOrder.get().price()){
                log.debug("Executing BUY Order - {} and SELL Order - {}",
                        buyOrder.get().orderId(),
                        order.orderId());

                executeTrade(buyOrder.get(), order);
            }
        }
    }

    private void checkBuyOrder(Order order) {
        if(OrderType.BUY.equals(order.orderType())){
            Optional<Order> sellOrder = orderBookService.peekSellOrder();
            
            if(sellOrder.isPresent() && order.price() >= sellOrder.get().price()){
                log.debug("Executing BUY Order - {} and SELL Order - {}", 
                        order.orderId(), 
                        sellOrder.get().orderId());
                
                executeTrade(order, sellOrder.get());
            }
        }
    }

    @Override
    public void executeTrade(Order buyOrder, Order sellOrder) {
        int quantity = Math.min(buyOrder.quantity(), sellOrder.quantity());

        if (buyOrder.quantity() > quantity){
            Order modifiedBuyOrder = new Order(buyOrder.orderId(),
                    buyOrder.orderType(),
                    buyOrder.price(),
                    buyOrder.quantity() - quantity);
            orderBookService.modifyFirstBuyOrder(modifiedBuyOrder);
        }
        else {
            orderBookService.removeBuyOrder();
        }

        if (sellOrder.quantity() > quantity){
            Order modifiedSellOrder = new Order(sellOrder.orderId(),
                    sellOrder.orderType(),
                    sellOrder.price(),
                    sellOrder.quantity() - quantity);
            orderBookService.modifyFirstSellOrder(modifiedSellOrder);
        }
        else {
            orderBookService.removeSellOrder();
        }
    }
}
