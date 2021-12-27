package com.aptiq.taskschedulter.taskmanager;

import java.util.concurrent.atomic.AtomicInteger;

public class ProcessCounter {

    static AtomicInteger processCounter = new AtomicInteger(Integer.MIN_VALUE);

    public static synchronized int getProcessCounter() {
        return processCounter.incrementAndGet();
    }
}
