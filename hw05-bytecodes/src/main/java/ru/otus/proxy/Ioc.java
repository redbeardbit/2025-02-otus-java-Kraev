package ru.otus.proxy;

import ru.otus.cache.MethodCache;
import ru.otus.logger.LoggingInvocationHandler;
import ru.otus.logger.SillyLogger;

public class Ioc {

    private Ioc() {
        // Private constructor to hide the implicit public one
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
        return Reflection.newProxy(
                interfaceType, new LoggingInvocationHandler(target, new SillyLogger(), MethodCache.INSTANCE));
    }
}
