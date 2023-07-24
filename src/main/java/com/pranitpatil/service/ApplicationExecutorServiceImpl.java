package com.pranitpatil.service;

import com.pranitpatil.dto.Order;

import java.io.InputStream;
import java.util.Optional;

public class ApplicationExecutorServiceImpl implements ApplicationExecutorService {

    private IOService ioService;
    private OrderBookService orderBookService;
    private OrderMatcherService orderMatcherService;
    private TradeBookService tradeBookService;

    public ApplicationExecutorServiceImpl(InputStream inputStream) {
        ioService = new ConsoleIOService(inputStream);
        orderBookService = OrderBookStorageService.getInstance();
        orderMatcherService = OrderMatcherServiceImpl.getInstance();
        tradeBookService = TradeBookStorageService.getInstance();
    }

    @Override
    public void startApplication() {
        System.out.println("Please enter all orders separated by comma, leave the line blank once done.");
        Optional<Order> order;
        while ((order = ioService.readOrder()).isPresent()) {

            Order savedOrder = switch (order.get().orderType()) {
                case BUY -> orderBookService.addBuyOrder(order.get());
                case SELL -> orderBookService.addSellOrder(order.get());
            };

            orderMatcherService.matchOrder(savedOrder);
        }

        ioService.getOrderBookOutput();

        //Optional : Print Trade book
        boolean printTradeBookOutput = false;
        if (printTradeBookOutput) {
            System.out.println("--------TRADE BOOK--------");
            System.out.println(tradeBookService.printTradeBook());
        }
    }
}
