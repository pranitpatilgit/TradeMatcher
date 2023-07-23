package com.pranitpatil.service;

import com.pranitpatil.exception.TradeMatcherException;
import com.pranitpatil.dto.Order;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Console based implementation of {@link IOService}
 * Takes the input from command line
 */
public class ConsoleIOService implements IOService {
    
    private OrderBookService orderBookService;
    private Scanner scanner;
    private OrderMapper orderMapper;

    public ConsoleIOService(InputStream inputStream) {
        orderBookService = OrderBookStorageService.getInstance();
        scanner = new Scanner(inputStream);
        orderMapper = OrderMapper.getInstance();
    }

    @Override
    public List<Order> getOrderInput(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);

        List<Order> orders = new ArrayList<>();

        try {
            String line;
            while (!(line = scanner.nextLine()).isEmpty()) {
                orders.add(orderMapper.mapOrderFromCSV(line));
            }
        } catch (TradeMatcherException e) {
            throw e;
        } catch (Exception e) {
            throw new TradeMatcherException("Error occurred while taking order input.", e);
        }

        return orders;
    }

    @Override
    public Optional<Order> readOrder() {
        try {
            String line = scanner.nextLine();
            if (!line.isEmpty()) {
                return Optional.of(orderMapper.mapOrderFromCSV(line));
            }
            else {
                return Optional.empty();
            }
        } catch (TradeMatcherException e) {
            throw e;
        } catch (Exception e) {
            throw new TradeMatcherException("Error occurred while taking order input.", e);
        }
    }

    @Override
    public void getOrderBookOutput() {
        System.out.println(orderBookService.printOrderBook());
    }
}
