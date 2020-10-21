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
    private List<CommandExecutionData> commands;
    private CommandExecutionData activeCommand;
    private long timestamp;
    private boolean enabled;

    private static final FileController fc = FileController.getInstance();
    private static final ExecutionProfiler INSTANCE = new ExecutionProfiler();

    private ExecutionProfiler() {
        timestamp = Instant.now().toEpochMilli();
        commands = new ArrayList<CommandExecutionData>(50);
        Map<String,Object> options = fc.readAsMap(Client.getClientPath(), "profiling.json", String.class, Object.class);
        if (options.get("persistent profiling") != null) {
            enabled = (boolean) options.get("persistent profiling");
        }
    }

    public static ExecutionProfiler getInstance() {
        return INSTANCE;
    }

    @JsonCreator
    public static ExecutionProfiler getInstance(@JsonProperty("tasks") List<CommandExecutionData> tasks, @JsonProperty("activeTask") CommandExecutionData activeTask, @JsonProperty("timestamp") long timestamp) {
        INSTANCE.setActiveTask(activeTask);
        INSTANCE.setTasks(tasks);
        INSTANCE.setTimestamp(timestamp);
        return INSTANCE;
    }

     public <T> void start(Class<T> c, String task) {
        if (enabled) {
            if (activeCommand == null) {
                activeCommand = new CommandExecutionData(commands.size(), task);
            }
            activeCommand.start(c.getSimpleName(), task);
        }
    }

    public void addGasCost(BigInteger gas) {
        if (enabled) {
            activeCommand.addGasCost(gas.longValue());
        }
    }

    public void end() {
        if (enabled) {
            activeCommand.end();
            if (activeCommand.finished()) {
                commands.add(activeCommand);
                activeCommand = null;
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

    public CommandExecutionData activeTask() {
        return activeCommand;
    }

    public List<CommandExecutionData> getTasks() {
        return commands;
    }

    public long getTimestamp() {
        return timestamp;
    }

    /*
     * SETTERS
     */

    private void setActiveTask(CommandExecutionData activeTask) {
        this.activeCommand = activeTask;
    }

    private void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private void setTasks(List<CommandExecutionData> tasks) {
        this.commands = tasks;
    }

    @Override
    public String toString() {
        String base_str = "{\"timestamp\": %d, \"tasks\": [\n%s\n]}";
        List<String> tasks_str = new ArrayList<String>(commands.size());
        commands.forEach((t) -> tasks_str.add(t.toString()));
        return String.format(base_str, timestamp, String.join(",\n", tasks_str));
    }
}
