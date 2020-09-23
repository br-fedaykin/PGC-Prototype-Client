package com.brunoarruda.hyperdcpabe.monitor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TaskExecutionData {
    private final long timestamp;
    private final int taskID;
    private final String label;
    private final List<MethodExecutionData> methodCalling;
    private long execTime;
    int currentMethod;

    public TaskExecutionData (int taskID, String label) {
        this.taskID = taskID;
        this.label = label;
        this.methodCalling = new ArrayList<MethodExecutionData>(20);
        this.timestamp = Instant.now().toEpochMilli();
        currentMethod = -1;
    }

    public void start(String className, String task) {
        MethodExecutionData m = new MethodExecutionData(className, task, currentMethod);
        m.start();
        methodCalling.add(m);
        currentMethod = methodCalling.size() - 1;
    }

    public void end() {
        methodCalling.get(currentMethod).end();
        currentMethod = methodCalling.get(currentMethod).getParentMethod();
        if (finished()) {
            long last_end = methodCalling.get(methodCalling.size() - 1).getEnd();
            execTime = last_end - methodCalling.get(0).getStart();
        }
    }

    public boolean finished() {
        return currentMethod < 0;
    }

    public boolean isRunning() {
        return ! finished();
    }

    @Override
    public String toString() {
        String base_str = "{\n#: %d, task: %s, execTime: %d, methodCalling: [\n\t%s\n\t]\n}";
        List<String> methodCalling_str = new ArrayList<String>(methodCalling.size());
        methodCalling.forEach((m) -> methodCalling_str.add(m.toString()));
        return String.format(base_str, taskID, label, execTime, String.join(",\n\t", methodCalling_str));
    }
}
