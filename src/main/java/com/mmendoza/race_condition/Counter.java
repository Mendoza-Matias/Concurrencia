package com.mmendoza.race_condition;

import java.util.concurrent.locks.ReentrantLock;

public class Counter {

    private ReentrantLock lock = new ReentrantLock();
    private int count = 0;
    // private AtomicInteger count = new AtomicInteger(0); 

    public void increment(){
        lock.lock(); // Bloqueo para asegurar que solo un hilo pueda incrementar el contador a la vez
        try {
            this.count++;
        } finally {
            lock.unlock(); // Liberar el bloqueo para permitir que otros hilos puedan acceder al método increment()
        }
    }

    public int getCount() {
        lock.lock(); // Bloqueo para asegurar que se lea el valor actualizado del contador
        try {
            return this.count;
        } finally {
            lock.unlock();
        }
    }
}
