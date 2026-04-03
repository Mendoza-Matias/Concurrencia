package com.mmendoza.race_condition;

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();

        Task task = new Task(counter);

        for (int i = 0; i < 1000; i++) {
            new Thread(task).start(); //Creación de 1000 hilos para ejecutar la tarea
        }

        //Tiempo para que el hilo termine su ejecución
        System.out.println("Tiempo para que el hilo termine su ejecución " + System.currentTimeMillis());

        System.out.println(counter.getCount());
    }
}
