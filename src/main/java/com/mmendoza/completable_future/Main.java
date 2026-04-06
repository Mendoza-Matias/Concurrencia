package com.mmendoza.completable_future;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mmendoza.repository.Calculator;
import com.mmendoza.repository.Notificator;
import com.mmendoza.repository.Order;
import com.mmendoza.repository.OrderRepository;
import com.mmendoza.repository.User;
import com.mmendoza.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        // Inicialización de servicios y repositorios
        UserRepository userRepository = new UserRepository();
        OrderRepository orderRepository = new OrderRepository();
        Calculator calculator = new Calculator();
        Notificator notificator = new Notificator();

        // Executor personalizado para usar con ambos enfoques
        ExecutorService executor = Executors.newFixedThreadPool(5);

        System.out.println("=== ENFOQUE 1: CompletableFuture (Moderno) ===");
        long inicioCF = System.currentTimeMillis();

        CompletableFuture.supplyAsync(() -> userRepository.findById(1), executor)
                .thenCompose(user -> CompletableFuture.supplyAsync(() -> orderRepository.findByUser(user), executor))
                .thenApply(calculator::calculateTotal)
                .thenAccept(total -> {
                    System.out.println("Enviando email...");
                    notificator.sendEmail(total);
                })
                .exceptionally(e -> {
                    System.out.println("Ocurrió un error: " + e.getMessage());
                    return null;
                })
                .join(); // Esperamos a que la cadena termine

        long finCF = System.currentTimeMillis();
        System.out.println("Tiempo CompletableFuture: " + (finCF - inicioCF) + "ms");

        System.out.println("\n------------------------------------------------\n");

        System.out.println("=== ENFOQUE 2: ExecutorService (Tradicional) ===");
        long inicioES = System.currentTimeMillis();

        try {
            // Enviamos la tarea y esperamos su finalización explícitamente para medir
            // tiempo real
            executor.submit(() -> {
                User user = userRepository.findById(1);
                List<Order> orders = orderRepository.findByUser(user);
                Double total = calculator.calculateTotal(orders);
                notificator.sendEmail(total);
            }).get(); // El .get() bloquea hasta que la tarea termine
        } catch (Exception e) {
            System.err.println("Error en ExecutorService: " + e.getMessage());
        }

        long finES = System.currentTimeMillis();
        System.out.println("Tiempo ExecutorService: " + (finES - inicioES) + "ms");

        // Importante: No olvidar cerrar el executor
        executor.shutdown();
    }
}
