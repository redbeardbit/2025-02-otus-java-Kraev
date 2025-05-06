package ru.otus.tests.kit.engine;

import java.lang.reflect.Method;

public class TestTask {

    private final String displayName;

    private final MethodType methodType;

    private final Method method;

    private Status state;

    TestTask(String displayName, Method method, MethodType methodType) {
        this.displayName = displayName;
        this.method = method;
        this.methodType = methodType;
        this.state = Status.PREPARED;
    }

    public String getDisplayName() {
        return displayName;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public Method getMethod() {
        return method;
    }

    public Status getState() {
        return state;
    }

    public void setState(Status state) {
        this.state = state;
    }
}
