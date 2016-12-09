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
}