package com.mmendoza.race_condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();
        ExecutorService executor = Executors.newFixedThreadPool(1000);

        long inicio = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            executor.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.incrementSynchronized();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long fin = System.currentTimeMillis();

        System.out.println("Tiempo: " + (fin - inicio) + "ms");

        System.out.println("Valor final: " + counter.getCountSynchronized());
    }
}
