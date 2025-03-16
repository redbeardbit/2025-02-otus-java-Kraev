package ru.otus.proxy;

import ru.otus.Logger.LoggingInvocationHandler;
import ru.otus.Logger.SillyLogger;

public class Ioc {

    public Ioc() {
    }

    public static <T> T newLoggedInstance(T target, Class<T> interfaceType) {
        if (target == null) {
            throw new NullPointerException("Target cannot be null");
        }
        if (interfaceType == null) {
            throw new NullPointerException("Interface type cannot be null");
        }
        if (!interfaceType.isInterface()) {
            throw new IllegalArgumentException("Interface type must be an interface");
        }
        return Reflection.newProxy(interfaceType, new LoggingInvocationHandler(target, new SillyLogger()));
    }
}
