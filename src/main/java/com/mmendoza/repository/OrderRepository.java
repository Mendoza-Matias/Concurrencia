package com.mmendoza.repository;

import java.util.List;

public class OrderRepository {
    private final List<Order> orders = List.of(
            new Order(1, "Order-1", new User(1, "Juan"), 100.0),
            new Order(2, "Order-2", new User(1, "Juan"), 200.0),
            new Order(3, "Order-3", new User(2, "Maria"), 300.0),
            new Order(4, "Order-4", new User(2, "Maria"), 400.0),
            new Order(5, "Order-5", new User(3, "Pedro"), 500.0),
            new Order(6, "Order-6", new User(3, "Pedro"), 600.0),
            new Order(7, "Order-7"),
            new Order(8, "Order-8"),
            new Order(9, "Order-9"),
            new Order(10, "Order-10"),
            new Order(11, "Order-11"),
            new Order(12, "Order-12"),
            new Order(13, "Order-13"),
            new Order(14, "Order-14"),
            new Order(15, "Order-15"),
            new Order(16, "Order-16"),
            new Order(17, "Order-17"),
            new Order(18, "Order-18"),
            new Order(19, "Order-19"),
            new Order(20, "Order-20"));

    public List<Order> findAll() {
        return orders;
    }

    public List<Order> findByUser(User user) {
        try {
            Thread.sleep(1000); // simula latencia de red
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // interrumpe el hilo
        }
        return orders.stream()
                .filter(order -> order.getUser() != null && order.getUser().getId().equals(user.getId())).toList();
    }
}
