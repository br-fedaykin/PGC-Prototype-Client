package com.brunoarruda.hyperdcpabe.monitor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class TaskExecutionData {
    private final long timestamp;
    private final int taskID;
    private final String label;
    private final List<MethodExecutionData> methodStack;
    private long execTime;
    int currentMethod;

    public TaskExecutionData (int taskID, String label) {
        this.taskID = taskID;
        this.label = label;
        this.methodStack = new ArrayList<MethodExecutionData>(20);
        this.timestamp = Instant.now().toEpochMilli();
        currentMethod = -1;
    }

    public void start(String className, String task) {
        MethodExecutionData m = new MethodExecutionData(className, task, currentMethod);
        m.start();
        methodStack.add(m);
        currentMethod = methodStack.size() - 1;
    }

    public void end() {
        MethodExecutionData m = methodStack.get(currentMethod);
        m.end();
        currentMethod = m.getParentMethod();

        if (finished()) {
            long last_end = methodStack.get(methodStack.size() - 1).getEnd();
            execTime = last_end - methodStack.get(0).getStart();
        } else {
            methodStack.get(currentMethod).subtractTime(m.getExecTime());
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
        List<String> methodCalling_str = new ArrayList<String>(methodStack.size());
        methodStack.forEach((m) -> methodCalling_str.add(m.toString()));
        return String.format(base_str, taskID, label, execTime, String.join(",\n\t", methodCalling_str));
    }
}
