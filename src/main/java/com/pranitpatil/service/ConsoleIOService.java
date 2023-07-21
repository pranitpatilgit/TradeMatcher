package com.pranitpatil.service;

import com.pranitpatil.exception.TradeMatcherException;
import com.pranitpatil.dto.Order;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console based implementation of {@link IOService}
 * Takes the input from command line
 */
public class ConsoleIOService implements IOService {

    private static final ConsoleIOService INSTANCE = new ConsoleIOService();

    private OrderMapper orderMapper = OrderMapper.getInstance();

    private ConsoleIOService() {
    }

    public static ConsoleIOService getInstance() {
        return INSTANCE;
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
    public void getOrderBookOutput() {

    }
}
