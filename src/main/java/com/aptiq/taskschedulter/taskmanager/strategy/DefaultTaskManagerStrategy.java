package com.aptiq.taskschedulter.taskmanager.strategy;

import com.aptiq.taskschedulter.process.Process;
import com.aptiq.taskschedulter.process.ProcessPool;

import java.util.concurrent.ConcurrentHashMap;

public class DefaultTaskManagerStrategy implements TaskManagerStrategy {


    @Override
    public ProcessPool initialize(final int capacity) {
        return new ProcessPool(ConcurrentHashMap.newKeySet(), capacity);
    }

    public int add(final ProcessPool processes, final Process process) {
        if (processes.isPoolFull()) {
            System.out.println("Queue is full!");
            return 1;
        }

        processes.getProcesses().add(process);
        System.out.println("added process PID:" + process.getPid());
        return 0;
    }
}
