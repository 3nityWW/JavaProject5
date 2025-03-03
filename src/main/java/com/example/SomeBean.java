package com.example;

public class SomeBean {
    @AutoInject
    private SomeInterface field1;

    @AutoInject
    private SomeOtherInterface field2;

    public void foo() {
        field1.doSomething();
        field2.doSomeOther();
    }
}