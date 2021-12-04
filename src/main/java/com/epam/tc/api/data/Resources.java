package com.epam.tc.api.data;

public enum Resources {

    BOARDS("/1/boards/"),
    CARDS("/1/cards/"),
    LISTS("/1/lists/"),
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
