package com.brunoarruda.hyperdcpabe.monitor;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

class MethodExecutionData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String className, method;
    private final MethodExecutionData parentMethod;
    private long start, end, execTime, execTimeLiquid, gasCost;

    @JsonCreator
    public MethodExecutionData(@JsonProperty("class") String className, @JsonProperty("method") String method, @JsonProperty("start") long start, @JsonProperty("end") long end, @JsonProperty("execTime") long execTime, @JsonProperty("execTimeLiquid") long execTimeLiquid, @JsonProperty("gasCost") long gasCost) {
        this.className = className;
        this.method = method;
        this.start = start;
        this.end = end;
        this.execTime = execTime;
        this.execTimeLiquid = execTimeLiquid;
        this.gasCost = gasCost;
        this.parentMethod = null;
    }

    public MethodExecutionData(String className, String method, MethodExecutionData parentMethod) {
        this.className = className;
        this.method = method;
        this.parentMethod = parentMethod;
    }

    public void start() {
        start = Instant.now().toEpochMilli();
    }

    public void end() {
        end = Instant.now().toEpochMilli();
        execTime = end - start;
        execTimeLiquid += execTime;
    }

	public void subtractTime(long execTime) {
        execTimeLiquid -= execTime;
	}

	public void addGasCost(long gas) {
        gasCost += gas;
    }

    /*
     * GETTERS
     */
    @JsonIgnore
    public MethodExecutionData getParentMethod() {
        return parentMethod;
    }

    public String getMethod() {
        return method;
    }

    @JsonProperty("class")
    public String getClassName() {
        return className;
    }

    public long getEnd() {
        return end;
    }

    public long getStart() {
        return start;
    }

    public long getGasCost() {
        return gasCost;
    }

    public long getExecTime() {
        return execTime;
    }

    public long getExecTimeLiquid() {
        return execTimeLiquid;
    }

    @Override
    public String toString() {
        String base_str = "{(liquid) execTime: %d, gasCost: %d, start: %d, end: %d, method: %s.%s}";
        return String.format(base_str, execTimeLiquid, gasCost, start, end, className, method);
    }
}
