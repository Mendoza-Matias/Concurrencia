package com.mmendoza.race_condition;

public class Task implements Runnable {

    private Counter counter;

    public Task(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
       for(int i = 0; i < 1000; i++){ //llamo al metodo 1000 veces para incrementar el contador
           counter.increment();
       }
    }
}