package com.aptiq.taskschedulter.process;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ProcessPriority {
    HIGH(0),
    MEDIUM(1),
    LOW(2);

    private final int value;

    public static ProcessPriority of(final int i) {
        return Arrays.stream(ProcessPriority.values())
                .filter(processPriority -> processPriority.value == i)
                .findAny()
                .orElse(null);
    }
}
