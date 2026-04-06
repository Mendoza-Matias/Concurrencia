package com.mmendoza.executor_service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.mmendoza.repository.UserRepository;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        UserRepository repo = new UserRepository();

        List<Integer> ids = IntStream.range(0, 20) // Crea una lista de 20 números para simular la busqueda de usuarios
                .boxed().collect(Collectors.toList());

        // ── Versión secuencial ──────────────────────────────
        long inicioSeq = System.currentTimeMillis();

        for (int id : ids) {
            System.out.println(repo.findById(id));
        }

        long finSeq = System.currentTimeMillis();
        System.out.println("Secuencial: " + (finSeq - inicioSeq) + "ms");

        // ── Versión paralela ────────────────────────────────
        long inicioPar = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int id : ids) {
            final int userId = id;
            executor.execute(() -> // cada ID en un hilo del pool
            System.out.println(repo.findById(userId)));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES); // esperar que terminen

        long finPar = System.currentTimeMillis();
        System.out.println("Paralelo (5 hilos): " + (finPar - inicioPar) + "ms");
    }
}
