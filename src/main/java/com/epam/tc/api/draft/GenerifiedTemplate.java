package com.epam.tc.api.draft;

abstract class GenerifiedTemplate<T> {
    public void templateMethod() {
        foo();
        goo();
    }

    public abstract void foo();

    public abstract void goo();
}
