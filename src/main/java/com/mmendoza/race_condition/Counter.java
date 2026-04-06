package com.mmendoza.race_condition;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {

    private AtomicInteger atomicCounter = new AtomicInteger(0);
    private ReentrantLock lock = new ReentrantLock();
    private int countSynchronized = 0;
    private int countLock = 0;

    public void incrementAtomic() {
        atomicCounter.getAndIncrement();
    }

    public synchronized void incrementSynchronized() {
        countSynchronized++;
    }

    public void incrementLock() {
        lock.lock();
        try {
            countLock++;
        } finally {
            lock.unlock();
        }
    }

    public int getCountAtomic() {
        return atomicCounter.get();
    }

    public synchronized int getCountSynchronized() {
        return countSynchronized;
    }

    public int getCountLock() {
        return countLock;
    }
}
