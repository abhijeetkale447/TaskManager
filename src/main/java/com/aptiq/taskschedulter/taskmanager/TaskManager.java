package com.aptiq.taskschedulter.taskmanager;

import com.aptiq.taskschedulter.process.Process;
import com.aptiq.taskschedulter.process.ProcessPriority;
import com.aptiq.taskschedulter.process.ProcessPool;
import com.aptiq.taskschedulter.taskmanager.strategy.TaskManagerStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TaskManager {

    private ProcessPool processes;
    private TaskManagerStrategy taskManagerStrategy;

    public TaskManager(final TaskManagerStrategy taskManagerStrategy, final int capacity) {
        this.taskManagerStrategy = taskManagerStrategy;
        processes = taskManagerStrategy.initialize(capacity);
    }

    public synchronized int add(final Process process) {
        return taskManagerStrategy.add(processes, process);
    }

    public List<Process> listRunningProcessesOrderByTime() {
        return processes.getProcesses().stream()
                .sorted(Comparator.comparing(Process::getProcessCount))
                .collect(Collectors.toList());
    }

    public List<Process>  listRunningProcessesOrderByPriority() {
        return processes.getProcesses().stream()
                .sorted(Comparator.comparing(Process::getProcessPriority))
                .collect(Collectors.toList());
    }

    public List<Process>  listRunningProcessesOrderById() {
        return processes.getProcesses().stream()
                .sorted(Comparator.comparing(Process::getPid))
                .collect(Collectors.toList());
    }

    public int killAll() {
        final AtomicInteger killedCount = new AtomicInteger();

        processes.getProcesses().stream()
                .peek(ignore -> killedCount.getAndIncrement())
                .forEach(process -> process.kill());
        boolean isProcessKilled = killedCount.intValue() > 0;
        String message = isProcessKilled
                ? "killed " + killedCount + " processes." : "No processes were killed";
        System.out.println(message);
        return isProcessKilled ? 0 : 1;
    }

    public int kill(final String pid) {
        final AtomicInteger killedCount = new AtomicInteger();
        processes.getProcesses().stream()
                .filter(process -> process.getPid().equals(pid))
                .peek(ignore -> killedCount.getAndIncrement())
                .forEach(process -> process.kill());
        boolean isProcessKilled = killedCount.intValue() > 0;
        String message = isProcessKilled
                ? "killed " + killedCount + " processes with id: " + pid + "!"
                : "No process was killed with ID :" + pid;
        System.out.println(message);
        return isProcessKilled ? 0 : 1;
    }

    public int kill(final ProcessPriority processPriority) {
        final AtomicInteger killedCount = new AtomicInteger();
        processes.getProcesses().stream()
                .filter(process -> process.getProcessPriority().equals(processPriority))
                .peek(ignore -> killedCount.getAndIncrement())
                .forEach(process -> process.kill());
        boolean isProcessKilled = killedCount.intValue() > 0;
        String message = isProcessKilled
                ? "killed " + killedCount + " processes with priority: " + processPriority.name() + "!"
                : "No process was killed with priority :" + processPriority.name();
        System.out.println(message);
        return isProcessKilled ? 0 : 1;
    }
}
