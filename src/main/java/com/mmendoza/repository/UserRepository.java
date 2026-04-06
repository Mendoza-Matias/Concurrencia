package com.mmendoza.repository;

import java.util.List;

public class UserRepository {
    private final List<User> users = List.of(
            new User(1, "Juan", List.of(new Order(1, "Order-1"), new Order(2, "Order-2"))),
            new User(2, "Maria", List.of(new Order(3, "Order-3"), new Order(4, "Order-4"))),
            new User(3, "Pedro", List.of(new Order(5, "Order-5"), new Order(6, "Order-6"))),
            new User(4, "Ana", List.of(new Order(7, "Order-7"), new Order(8, "Order-8"))),
            new User(5, "Luis", List.of(new Order(9, "Order-9"), new Order(10, "Order-10"))),
            new User(6, "Maria", List.of(new Order(11, "Order-11"), new Order(12, "Order-12"))),
            new User(7, "Pedro"),
            new User(8, "Ana"),
            new User(9, "Luis"),
            new User(10, "Maria"),
            new User(11, "Pedro"),
            new User(12, "Ana"),
            new User(13, "Luis"),
            new User(14, "Maria"),
            new User(15, "Pedro"),
            new User(16, "Ana"),
            new User(17, "Luis"),
            new User(18, "Maria"),
            new User(19, "Pedro"),
            new User(20, "Ana"));

    public List<User> findAll() {
        return users;
    }

    public User findById(int id) {
        try {
            Thread.sleep(1000); // simula latencia de red
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return users.stream().filter(user -> user.getId() == id).findFirst().get();
    }
}