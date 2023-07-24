package com.pranitpatil.storage;

import com.pranitpatil.dto.Order;
import lombok.Getter;

import java.util.Comparator;
import java.util.PriorityQueue;

@Getter
public class OrderBook {

    private PriorityQueue<Order> buyQueue;
    private PriorityQueue<Order> sellQueue;

    public OrderBook() {
        buyQueue = new PriorityQueue<>(Comparator.comparingInt(Order::price).reversed().thenComparing(Order::createdAt));
        sellQueue = new PriorityQueue<>(Comparator.comparingInt(Order::price).thenComparing(Order::createdAt));
    }
}
