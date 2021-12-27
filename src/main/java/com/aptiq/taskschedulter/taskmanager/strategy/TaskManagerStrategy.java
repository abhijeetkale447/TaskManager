package com.aptiq.taskschedulter.taskmanager.strategy;

import com.aptiq.taskschedulter.process.Process;
import com.aptiq.taskschedulter.process.ProcessPool;

public interface TaskManagerStrategy {
    ProcessPool initialize(int capacity);
    int add(ProcessPool processPool, Process process);
}
