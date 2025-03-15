package ru.otus.proxy;

import ru.otus.Logger.LoggingInvocationHandler;
import ru.otus.Logger.SillyLogger;
import ru.otus.TestLogging;
import ru.otus.TestLoggingImpl;

public class Ioc {

    public Ioc() {
    }

    public static <T> T newInstance(T target, Class<T> interfaceType) {
        if ((interfaceType == null) && (!interfaceType.isInterface())) {
            return null;
        }
        return Reflection.newProxy(interfaceType, new LoggingInvocationHandler(target, new SillyLogger()));
    }

    public static TestLogging newTestLogging() {
        return Reflection.newProxy(
                TestLogging.class,
                new LoggingInvocationHandler(new TestLoggingImpl(), new SillyLogger()));
    }
}
