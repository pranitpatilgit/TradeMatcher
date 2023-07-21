package com.pranitpatil.storage;

import com.pranitpatil.dto.Order;
import lombok.Getter;

import java.util.PriorityQueue;

@Getter
public class OrderBook {
    
    private PriorityQueue<Order> buyQueue;
    private PriorityQueue<Order> sellQueue;

    public OrderBook() {
        buyQueue = new PriorityQueue<>((o1, o2) -> o2.price() - o1.price());
        sellQueue = new PriorityQueue<>((o1, o2) -> o1.price() - o2.price());
    }
}
