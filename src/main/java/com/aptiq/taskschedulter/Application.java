package com.aptiq.taskschedulter;

import com.aptiq.taskschedulter.process.Process;
import com.aptiq.taskschedulter.process.ProcessPriority;
import com.aptiq.taskschedulter.taskmanager.TaskManager;
import com.aptiq.taskschedulter.taskmanager.TaskManagerFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Application {

    public static void main(String[] args) {
        runTest(TaskManagerFactory.createPriorityTaskManager(3));
        runTest(TaskManagerFactory.createFifoTaskManager(3));
        runTest(TaskManagerFactory.createDefaultTaskManager(3));

    }

    private static void runTest(final TaskManager taskManager) {
        Random random = new Random();

        List<Integer> pool =  IntStream.range(0, 10000000)
                .boxed()
                .collect(Collectors.toList());


        pool.parallelStream().forEach(i -> {
            Process process = new Process(String.valueOf(i), ProcessPriority.of(random.nextInt(100) % 3));
            System.out.println("trying to add " + process);
            taskManager.kill(String.valueOf(i-1));
            taskManager.add(process);
        });
        System.out.println(taskManager.listRunningProcessesOrderByTime());

        taskManager.killAll();
    }
}
