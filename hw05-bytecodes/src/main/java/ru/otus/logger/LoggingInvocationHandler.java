package ru.otus.logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import ru.otus.cache.MethodCache;
import ru.otus.cache.MethodType;
import ru.otus.proxy.Reflection;

public class LoggingInvocationHandler implements InvocationHandler {

    private final Object target;
    private final String clazzName;

    private final Logger logger;

    private MethodCache cache;

    public LoggingInvocationHandler(Object target, Logger logger, MethodCache cache) {
        this.target = target;
        this.logger = logger;
        this.cache = cache;
        this.clazzName = target.getClass().getTypeName();
        initCache(target);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var result = method.invoke(target, args);
        loggingMethodExecution(method, args);
        return result;
    }

    private void loggingMethodExecution(Method method, Object[] args) {
        var ch = cache.getMethod(Reflection.getMethodSignature(clazzName, method));
        ch.ifPresent(entry -> logger.log(getLogMessage(entry, args)));
    }

    private String getLogMessage(MethodType methodType, Object[] args) {
        StringBuilder paramsBuilder = new StringBuilder();
        for (int i = 0; i < methodType.parameters().length; ++i) {
            if (i > 0) {
                paramsBuilder.append(", ");
            }
            paramsBuilder.append(String.format("%s:%s", methodType.parameters()[i], args[i].toString()));
        }
        return String.format("executed method: %s, %s", methodType.methodName(), paramsBuilder);
    }

    private void initCache(Object target) {
        Optional<Map<String, MethodType>> methods = Reflection.getMethods(target);
        methods.ifPresent(map -> map.forEach((key, value) -> cache.putMethod(key, value)));
    }
}
