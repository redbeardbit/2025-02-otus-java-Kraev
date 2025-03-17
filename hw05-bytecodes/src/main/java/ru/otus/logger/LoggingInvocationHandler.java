package ru.otus.logger;

import ru.otus.proxy.Reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler implements InvocationHandler {

    private final Object target;

    private final Logger logger;

    public LoggingInvocationHandler(Object target, Logger logger) {
        this.target = target;
        this.logger = logger;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var result = method.invoke(target, args);
        loggingMethodExecution(method, args);
        return result;
    }

    private void loggingMethodExecution(Method method, Object[] args) {
        if (Reflection.isLogAnnotationPresent(target, method)) {
            logger.log(Reflection.getLogMessage(method, args));
        }
    }
}
