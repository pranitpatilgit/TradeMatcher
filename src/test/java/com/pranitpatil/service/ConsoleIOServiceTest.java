package com.pranitpatil.service;

import com.pranitpatil.dto.Order;
import com.pranitpatil.dto.OrderType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsoleIOServiceTest {

    private ConsoleIOService consoleIOService;

    @BeforeAll
    void init() throws URISyntaxException, IOException {
        Path inputFilePath = Paths.get(getClass().getClassLoader()
                .getResource("orders1.csv").toURI());
        InputStream inputStream = new ByteArrayInputStream((Files.readString(inputFilePath).getBytes()));
        consoleIOService = new ConsoleIOService(inputStream);
    }

    @Test
    void test_whenInputStreamProvided_thenReadAllOrders() {
        Optional<Order> order;
        List<Order> orders = new ArrayList<>();
        while ((order = consoleIOService.readOrder()).isPresent()) {
            orders.add(order.get());
        }

        assertEquals(5, orders.size());
        assertEquals("1", orders.get(0).orderId());
        assertEquals(99, orders.get(0).price());
        assertEquals(1000, orders.get(0).quantity());
        assertEquals(OrderType.BUY, orders.get(0).orderType());
        assertEquals(OrderType.SELL, orders.get(4).orderType());
    }
}
