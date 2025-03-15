package ru.otus.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;

public class Reflection {

    public static <T> T newProxy(Class<T> interfaceType, InvocationHandler handler) {
        if (!interfaceType.isInterface()) {
            throw new IllegalArgumentException("Class must be interface");
        }
        Object object = Proxy.newProxyInstance(
                interfaceType.getClassLoader(), new Class<?>[]{interfaceType}, handler
        );
        return interfaceType.cast(object);
    }

    public static String[] getParameterNames(Method method) {
        Parameter[] parameters = method.getParameters();
        String[] parameterNames = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parameterNames[i] = parameters[i].getName();
        }
        return parameterNames;
    }
}
