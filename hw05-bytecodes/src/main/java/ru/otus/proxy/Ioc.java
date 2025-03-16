package ru.otus.proxy;

import ru.otus.Logger.LoggingInvocationHandler;
import ru.otus.Logger.SillyLogger;

public class Ioc {

    public Ioc() {
    }

    public static <T> T newLoggedInstance(T target, Class<T> interfaceType) {
        if ((interfaceType == null) && (!interfaceType.isInterface())) {
            return null;
        }
        return Reflection.newProxy(interfaceType, new LoggingInvocationHandler(target, new SillyLogger()));
    }
}
