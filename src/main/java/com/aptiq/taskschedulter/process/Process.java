package com.aptiq.taskschedulter.process;

import com.aptiq.taskschedulter.taskmanager.ProcessCounter;
import lombok.Getter;

import java.util.Objects;

@Getter
public final class Process {
    private final String pid;

    @Override
    public String toString() {
        return "Process{" +
                "pid='" + pid + '\'' +
                ", processPriority=" + processPriority +
                ", creationTime=" + processCount +
                '}';
    }

    private final ProcessPriority processPriority;
    private long processCount;

    public Process(final String pid, final ProcessPriority processPriority) {
        this.pid = pid;
        this.processPriority = processPriority;
    }

    public void startProcess() {
        processCount = ProcessCounter.getProcessCounter();
    }

    public int kill() {
        System.out.println("Process " + pid + " killed!");
        return 0;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Process process = (Process) o;
        return pid.equals(process.pid) && processPriority == process.processPriority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, processPriority);
    }
}
