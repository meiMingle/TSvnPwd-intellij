package com.tomxin.tool.tangible;

public enum States {

    /**
     *
     */
    ExpectingKeyDef,
    /**
     *
     */
    ExpectingKeyName,
    /**
     *
     */
    ExpectingValueDef,
    /**
     *
     */
    ExpectingValue;

    public static final int SIZE = Integer.SIZE;

    public int getValue() {
        return this.ordinal();
    }

    public static States forValue(int value) {
        return values()[value];
    }
}
