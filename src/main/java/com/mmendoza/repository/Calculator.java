package com.mmendoza.repository;

import java.util.List;

public class Calculator {
    public double calculateTotal(List<Order> orders) {
        try {
            Thread.sleep(1000); // simula latencia de red
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return orders.stream().mapToDouble(Order::getAmount).sum();
    }
}
