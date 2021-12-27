package com.aptiq.taskschedulter.taskmanager;

import com.aptiq.taskschedulter.taskmanager.strategy.DefaultTaskManagerStrategy;
import com.aptiq.taskschedulter.taskmanager.strategy.FifoTaskManagerStrategy;
import com.aptiq.taskschedulter.taskmanager.strategy.PriorityTaskManagerStrategy;

public class TaskManagerFactory {

    public static TaskManager createFifoTaskManager(final int capacity) {
        return new TaskManager(new FifoTaskManagerStrategy(), capacity);
    }

    public static TaskManager createDefaultTaskManager(final int capacity) {
        return new TaskManager(new DefaultTaskManagerStrategy(), capacity);
    }

    public static TaskManager createPriorityTaskManager(final int capacity) {
        return new TaskManager(new PriorityTaskManagerStrategy(), capacity);
    }
}
