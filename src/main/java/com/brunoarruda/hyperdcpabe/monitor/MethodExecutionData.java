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
    private long start, end, execTime, execTimeLiquid, gasCost, gasPrice, gasLimit;
    private double etherCost;

    @JsonCreator
    public MethodExecutionData(@JsonProperty("class") String className, @JsonProperty("method") String method, @JsonProperty("start") long start, @JsonProperty("end") long end, @JsonProperty("execTime") long execTime, @JsonProperty("execTimeLiquid") long execTimeLiquid, @JsonProperty("gasCost") long gasCost, @JsonProperty("gasPrice") long gasPrice, @JsonProperty("etherCost") double etherCost, @JsonProperty("gasLimit") long gasLimit){
        this.className = className;
        this.method = method;
        this.start = start;
        this.end = end;
        this.execTime = execTime;
        this.execTimeLiquid = execTimeLiquid;
        this.gasCost = gasCost;
        this.gasPrice = gasPrice;
        this.etherCost = etherCost;
        this.gasLimit = gasLimit;
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

	public double addGasCost(long gas, long gasPrice) {
        this.gasCost = gas;
        this.gasPrice = gasPrice;
        this.etherCost = gas / (double) gasPrice;
        return etherCost;
    }

    public void saveGasLimit(long gasLimit) {
        this.gasLimit = gasLimit;
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

    public double getEtherCost() {
        return etherCost;
    }

    public long getGasPrice() {
        return gasPrice;
    }

    public long getGasLimit() {
        return gasLimit;
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
