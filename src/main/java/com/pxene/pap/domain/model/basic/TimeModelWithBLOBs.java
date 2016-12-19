package com.pxene.pap.domain.model.basic;

public class TimeModelWithBLOBs extends TimeModel {
    private byte[] week;

    private byte[] clock;

    public byte[] getWeek() {
        return week;
    }

    public void setWeek(byte[] week) {
        this.week = week;
    }

    public byte[] getClock() {
        return clock;
    }

    public void setClock(byte[] clock) {
        this.clock = clock;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", week=").append(week);
        sb.append(", clock=").append(clock);
        sb.append("]");
        return sb.toString();
    }
}