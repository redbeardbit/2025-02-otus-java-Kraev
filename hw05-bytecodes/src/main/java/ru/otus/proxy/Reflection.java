package ru.otus.proxy;

import ru.otus.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

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
        return Arrays.stream(parameters)
                .map(Parameter::getName)
                .toArray(String[]::new);
    }

    public static String getLogMessage(Method method, Object[] args) {
        String[] parameterNames = getParameterNames(method);
        String params = Arrays.stream(parameterNames)
                .map(name -> String.format("%s:%s", name, args[Arrays.asList(parameterNames).indexOf(name)]))
                .collect(Collectors.joining(", "));
        return String.format("executed method: %s, %s", method.getName(), params);
    }

    public static boolean isLogAnnotationPresent(Object target, Method method) {
        try {
            Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());

            if (targetMethod.isAnnotationPresent(Log.class)) {
                return true;
            }
        } catch (NoSuchMethodException e) {
            return false;
        }
        return false;
    }

}
