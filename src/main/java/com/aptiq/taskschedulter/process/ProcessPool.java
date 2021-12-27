package com.aptiq.taskschedulter.process;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProcessPool {
    private Set<Process> processes;
    private int capacity;

    public boolean isPoolFull() {
        return processes.size() >= capacity;
    }
}
