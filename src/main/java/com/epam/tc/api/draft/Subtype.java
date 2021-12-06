package com.epam.tc.api.draft;

class Subtype extends GenerifiedTemplate<Subtype> {

    public static String str = "Java";
    public String string = "Hello";

    public void foo() {
        System.out.println("foo from Subtype");
    }

    public void goo() {
        System.out.println("goo from Subtype");
    }
}
