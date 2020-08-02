package com.airlines.model;

public enum Status {

    CLOSED, RESERVED, SOLD, FREE;

    public static Status byOrdinal(int ord) {
        for (Status status : Status.values()) {
            if (status.ordinal() + 1 == ord) {
                return status;
            }
        }
        return null;
    }

    public int getIndex() {
        return ordinal() + 1;
    }

}
