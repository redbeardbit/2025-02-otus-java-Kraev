package ru.otus.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import ru.otus.annotation.Log;
import ru.otus.cache.MethodType;

public class Reflection {

    private Reflection() {
        // Private constructor to hide the implicit public one
    }

    public static <T> T newProxy(Class<T> interfaceType, InvocationHandler handler) {
        if (!interfaceType.isInterface()) {
            throw new IllegalArgumentException("Class must be interface");
        }
        Object object = Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class<?>[] {interfaceType}, handler);
        return interfaceType.cast(object);
    }

    public static Optional<Map<String, MethodType>> getMethods(Object target) {
        Map<String, MethodType> result = new HashMap<>();
        var methods = target.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Log.class)) {
                result.put(
                        getMethodSignature(target.getClass().getName(), method),
                        new MethodType(method.getName(), method.getParameters()));
            }
        }
        return Optional.ofNullable(result);
    }

    @SuppressWarnings("java:S3358")
    // implementation the same as java.lang.Class.methodToString
    public static String getMethodSignature(String className, Method method) {
        var argTypes = method.getParameters();

        return className
                + '.'
                + method.getName()
                + ((argTypes == null || argTypes.length == 0)
                        ? "()"
                        : Arrays.stream(argTypes)
                                .map(c -> c == null ? "null" : c.toString())
                                .collect(Collectors.joining(",", "(", ")")));
    }
}
