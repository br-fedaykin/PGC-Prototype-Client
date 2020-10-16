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
    private long gasCost;
    int currentMethodID;
    MethodExecutionData currentMethod;

    public TaskExecutionData (int taskID, String label) {
        this.taskID = taskID;
        this.label = label;
        this.methodStack = new ArrayList<MethodExecutionData>(20);
        this.timestamp = Instant.now().toEpochMilli();
        currentMethodID = -1;
    }

    public void start(String className, String task) {
        currentMethod = new MethodExecutionData(className, task, currentMethodID);
        currentMethod.start();
        methodStack.add(currentMethod);
        currentMethodID = methodStack.size() - 1;
    }

    public void end() {
        currentMethod.end();
        currentMethodID = currentMethod.getParentMethod();

        if (finished()) {
            long last_end = methodStack.get(methodStack.size() - 1).getEnd();
            execTime = last_end - methodStack.get(0).getStart();
        } else {
            long execTime = currentMethod.getExecTime();
            currentMethod = methodStack.get(currentMethodID);
            currentMethod.subtractTime(execTime);
        }
    }

    public void addGasCost(long gas) {
        currentMethod.addGasCost(gas);
        gasCost += gas;
    }

    public boolean finished() {
        return currentMethodID < 0;
    }

    public boolean isRunning() {
        return ! finished();
    }

    @Override
    public String toString() {
        String base_str = "{\n#: %d, task: %s, execTime: %d, gasCost: %d, methodCalling: [\n\t%s\n\t]\n}";
        List<String> methodCalling_str = new ArrayList<String>(methodStack.size());
        methodStack.forEach((m) -> methodCalling_str.add(m.toString()));
        return String.format(base_str, taskID, label, execTime, gasCost, String.join(",\n\t", methodCalling_str));
    }
}
