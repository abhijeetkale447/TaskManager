package com.aptiq.taskschedulter.taskmanager.strategy;

import com.aptiq.taskschedulter.process.Process;
import com.aptiq.taskschedulter.process.ProcessPool;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;

public class FifoTaskManagerStrategy implements TaskManagerStrategy {

    private static final Comparator<Process> fifoComparator =
            Comparator.comparing(Process::getProcessCount);

    @Override
    public ProcessPool initialize(final int capacity) {
        return new ProcessPool(new ConcurrentSkipListSet<Process>(fifoComparator), capacity);
    }

    @Override
    public int add(final ProcessPool processes, final Process process) {
        ConcurrentSkipListSet<Process> processPool = (ConcurrentSkipListSet<Process>) processes.getProcesses();

        if(processes.isPoolFull()) {
            Process oldestProcess = processPool.pollFirst();
            System.out.println("removed process PID:" + oldestProcess.getPid());
            processPool.remove(oldestProcess);
        }

        process.startProcess();
        processPool.add(process);
        System.out.println("added process PID:" + process.getPid());
        return 0;
    }
}
