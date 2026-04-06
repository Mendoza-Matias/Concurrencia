package com.mmendoza.deadlock;

public class Main {
    public static void main(String[] args) {

        Object thread1 = new Object();
        Object thread2 = new Object();

        // Hilo 1
        new Thread(() -> { // El hilo 1 adquiere el lock del recurso 1 y luego intenta adquirir el lock del
                           // recurso 2
            synchronized (thread1) {
                System.out.println("Adquiere el lock 1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("El hilo 1 esperando el lock del hilo 2");
                synchronized (thread2) { // puede adquirirlo porque el hilo 2 no lo tiene porque no ha entrado en el
                                         // synchronized
                    System.out.println("El hilo 1 posee ambos locks");
                }
                System.out.println("El hilo 1 libera ambos locks");
            }
        }).start();

        // Hilo 2
        new Thread(() -> {
            System.out.println("No arranca hasta que el hilo 1 libere el lock del recurso 1");
            synchronized (thread1) { // El hilo 2 tiene que esperar a que el hilo 1 libere el lock del recurso 1
                System.out.println("Adquiere el lock 1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("El hilo 2 esperando el lock del hilo 1");
                synchronized (thread2) { // thread1 tira error
                    System.out.println("El hilo 2 posee ambos locks");
                }
            }
        }).start();
    }
}
