package com.brunoarruda.hyperdcpabe.monitor;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brunoarruda.hyperdcpabe.Client;
import com.brunoarruda.hyperdcpabe.io.FileController;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecutionProfiler implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<TaskExecutionData> tasks;
    private TaskExecutionData activeTask;
    private long timestamp;
    private boolean enabled;

    private static final FileController fc = FileController.getInstance();
    private static final ExecutionProfiler INSTANCE = new ExecutionProfiler();

    private ExecutionProfiler() {
        timestamp = Instant.now().toEpochMilli();
        tasks = new ArrayList<TaskExecutionData>(50);
        Map<String,Object> options = fc.readAsMap(Client.getClientPath(), "profiling.json", String.class, Object.class);
        if (options.get("persistent profiling") != null) {
            enabled = (boolean) options.get("persistent profiling");
        }
    }

    public static ExecutionProfiler getInstance() {
        return INSTANCE;
    }

    @JsonCreator
    public static ExecutionProfiler getInstance(@JsonProperty("tasks") List<TaskExecutionData> tasks, @JsonProperty("activeTask") TaskExecutionData activeTask, @JsonProperty("timestamp") long timestamp) {
        INSTANCE.setActiveTask(activeTask);
        INSTANCE.setTasks(tasks);
        INSTANCE.setTimestamp(timestamp);
        return INSTANCE;
    }

     public <T> void start(Class<T> c, String task) {
        if (enabled) {
            if (activeTask == null) {
                activeTask = new TaskExecutionData(tasks.size(), task);
            }
            activeTask.start(c.getSimpleName(), task);
        }
    }

    public void addGasCost(BigInteger gas) {
        if (enabled) {
            activeTask.addGasCost(gas.longValue());
        }
    }

    public void end() {
        if (enabled) {
            activeTask.end();
            if (activeTask.finished()) {
                tasks.add(activeTask);
                activeTask = null;
            }
        }
    }

    public void writeToFile() {
        String filename = "execData-" + timestamp + ".json";
        fc.writeToDir("logs", filename, this);
    }

	public void enablePersistentProfiling() {
        enabled = true;
        Map<String, Object> config = new HashMap<>();
        config.put("persistent profiling", true);
        fc.writeToDir(Client.getClientPath(), "profiling.json", config);
	}

	public void disablePersistentProfiling() {
        enabled = false;
        Map<String, Boolean> config = new HashMap<>();
        config.put("persistent profiling", false);
        fc.writeToDir(Client.getClientPath(), "profiling.json", config);
    }

    /**
     * GETTERS
     */

    public TaskExecutionData activeTask() {
        return activeTask;
    }

    public List<TaskExecutionData> getTasks() {
        return tasks;
    }

    public long getTimestamp() {
        return timestamp;
    }

    /*
     * SETTERS
     */

    private void setActiveTask(TaskExecutionData activeTask) {
        this.activeTask = activeTask;
    }

    private void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private void setTasks(List<TaskExecutionData> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        String base_str = "{\"timestamp\": %d, \"tasks\": [\n%s\n]}";
        List<String> tasks_str = new ArrayList<String>(tasks.size());
        tasks.forEach((t) -> tasks_str.add(t.toString()));
        return String.format(base_str, timestamp, String.join(",\n", tasks_str));
    }
}
