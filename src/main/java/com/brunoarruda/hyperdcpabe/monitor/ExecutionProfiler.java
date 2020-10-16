package com.brunoarruda.hyperdcpabe.monitor;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ExecutionProfiler {

    List<TaskExecutionData> tasks;
    TaskExecutionData activeTask;
    private final long timestamp;

    private static final ExecutionProfiler INSTANCE = new ExecutionProfiler();

    private ExecutionProfiler() {
        timestamp = Instant.now().toEpochMilli();
        tasks = new ArrayList<TaskExecutionData>(50);
    }

    public static ExecutionProfiler getInstance() {
        return INSTANCE;
    }

    public <T> void start(Class<T> c, String task) {
        if (activeTask == null) {
            activeTask = new TaskExecutionData(tasks.size(), task);
        }
        activeTask.start(c.getSimpleName(), task);
    }

    public void addGasCost(BigInteger gas) {
        activeTask.addGasCost(gas.longValue());
    }

    public void end() {
        activeTask.end();
        if (activeTask.finished()) {
            tasks.add(activeTask);
            activeTask = null;
        }
    }

    @Override
    public String toString() {
        String base_str = "{\"timestamp\": %d, \"tasks\": [\n%s\n]}";
        List<String> tasks_str = new ArrayList<String>(tasks.size());
        tasks.forEach((t) -> tasks_str.add(t.toString()));
        return String.format(base_str, String.join(",\n", tasks_str));
    }
}
