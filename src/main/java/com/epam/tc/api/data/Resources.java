package com.epam.tc.api.data;

public enum Resources {

    TEMPLATE("/1/{resource}/"),
    ALL_MEMBERS_BOARDS("/1/members/iura_radomskii/boards/");

    private String name;

    Resources(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
