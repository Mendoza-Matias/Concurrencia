package com.mmendoza.repository;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;
    private String name;
    private List<Order> order;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.order = new ArrayList<>();
    }

    public User(Integer id, String name, List<Order> order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", order=" + order + "]";
    }
}
