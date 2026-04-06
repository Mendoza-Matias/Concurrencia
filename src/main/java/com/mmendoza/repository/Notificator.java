package com.mmendoza.repository;

public class Notificator {
    public void sendEmail(double total) {
        try {
            Thread.sleep(1000); // simula latencia de red
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Email sent with total: " + total);
    }
}
