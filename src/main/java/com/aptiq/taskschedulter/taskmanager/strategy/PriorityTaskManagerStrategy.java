package com.aptiq.taskschedulter.taskmanager.strategy;

import com.aptiq.taskschedulter.process.Process;
import com.aptiq.taskschedulter.process.ProcessPool;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;

public class PriorityTaskManagerStrategy implements TaskManagerStrategy {

    private static final Comparator<Process> processPriorityComparator =
            Comparator.comparing(Process::getProcessPriority, Comparator.reverseOrder())
            .thenComparing(Process::getProcessCount);


    @Override
    public ProcessPool initialize(final int capacity) {
        return new ProcessPool(new ConcurrentSkipListSet<>(processPriorityComparator), capacity);
    }

    @Override
    public int add(final ProcessPool processes, final Process process) {
        ConcurrentSkipListSet<Process> processQueue = (ConcurrentSkipListSet<Process>) processes.getProcesses();

        if(processes.isPoolFull()) {
            Process oldestProcess = processQueue.pollFirst();
            System.out.println("removed process PID:" + oldestProcess.getPid());
            processQueue.remove(oldestProcess);
        }

        process.startProcess();
        processQueue.add(process);
        System.out.println("added process PID:" + process.getPid());
        return 0;
    }
}
