package com.pranitpatil.service;

import com.pranitpatil.dto.Order;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Responsible for handling Input and output
 */
public interface IOService {

    /**
     * Used to get order input
     * Param inputStream can be used for testability. i.e - passing already existing values instead of command line
     * @param inputStream
     * @return
     */
    List<Order> getOrderInput(InputStream inputStream);
    
    Optional<Order> readOrder();
    
    void getOrderBookOutput();
}
