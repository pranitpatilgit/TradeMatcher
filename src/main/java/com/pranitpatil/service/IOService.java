package com.pranitpatil.service;

import com.pranitpatil.dto.Order;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Responsible for handling Input and output
 */
public interface IOService {
    
    Optional<Order> readOrder();
    
    void getOrderBookOutput();
}
