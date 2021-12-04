package com.epam.tc.api.data;

public enum ParameterNameValues {

    BLUE_BOARD("Blue Board"),
    RED_BOARD("Red Board"),
    GREEN_BOARD("Green Board");

    private String name;

    ParameterNameValues(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
