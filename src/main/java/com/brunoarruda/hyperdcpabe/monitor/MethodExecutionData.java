package com.brunoarruda.hyperdcpabe.monitor;

import java.time.Instant;

class MethodExecutionData {
    private final String className, method;
    private final int parentMethod;
    private long start, end, execTime, execTimeLiquid;

    public MethodExecutionData(String className, String method, int parentMethod) {
        this.className = className;
        this.method = method;
        this.parentMethod = parentMethod;
    }

    public int getParentMethod() {
        return parentMethod;
    }

    public String getMethod() {
        return method;
    }

    public String getClassName() {
        return className;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public void start() {
        setStart(Instant.now().toEpochMilli());
    }

    public void end() {
        setEnd(Instant.now().toEpochMilli());
        execTime = end - start;
        execTimeLiquid += execTime;
    }

    public long getExecTime() {
        return execTime;
    }

    @Override
    public String toString() {
        String base_str = "{(liquid) execTime: %d, method: %s.%s}";
        return String.format(base_str, execTimeLiquid, className, method);
    }

	public void subtractTime(long execTime) {
        execTimeLiquid -= execTime;
	}
}
