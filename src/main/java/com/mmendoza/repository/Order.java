package com.mmendoza.repository;

public class Order {
    private Integer id;
    private String name;
    private User user;
    private double amount;

    public Order(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Order(Integer id, String name, User user, double amount) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", name=" + name + " user=" + user + " amount=" + amount + "]";
    }
}
