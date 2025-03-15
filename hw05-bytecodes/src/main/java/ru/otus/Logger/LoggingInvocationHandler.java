package ru.otus.Logger;

import ru.otus.annotation.Log;
import ru.otus.proxy.Reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler implements InvocationHandler {

    Object target;

    OtusLogger logger;

    public LoggingInvocationHandler(Object target, OtusLogger logger) {
        this.target = target;
        this.logger = logger;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //TODO meta о аннотации от базового класса
        if (method.isAnnotationPresent(Log.class)) {
            logger.log(getLogMessage(method.getName(), Reflection.getParameterNames(method), args));
        }

        return method.invoke(target, args);
    }

    private String getLogMessage(String method, String[] parameterNames, Object[] args) {
        StringBuilder paramsBuilder = new StringBuilder();
        for (int i = 0; i < parameterNames.length; ++i) {
            if (i > 0) {
                paramsBuilder.append(", ");
            }
            paramsBuilder.append(String.format("%s:%s", parameterNames[i], args[i].toString()));
        }
        return String.format("executed method: %s, %s", method, paramsBuilder);
    }

}
