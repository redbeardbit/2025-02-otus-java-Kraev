package ru.otus.tests.kit.reflection;

import static java.util.stream.Collectors.toCollection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import ru.otus.tests.kit.annotations.DisplayName;
import ru.otus.tests.kit.preconditions.Preconditions;

public class ReflectionSupport {

    public static final String CLASS_MUST_NOT_BE_NULL = "Class must not be null";

    private ReflectionSupport() {}

    public static List<Method> getDeclaredMethods(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        Preconditions.notNull(clazz, CLASS_MUST_NOT_BE_NULL);

        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .collect(toCollection(ArrayList::new));
    }

    public static String getAnnotationDisplayNameValue(Method method) {
        String value = null;
        if (method.isAnnotationPresent(DisplayName.class)) {
            value = method.getAnnotation(DisplayName.class).value();
        }
        return Optional.ofNullable(value).orElse(method.getName());
    }

    public static Object newInstance(Class<?> clazz) {
        Preconditions.notNull(clazz, CLASS_MUST_NOT_BE_NULL);

        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new ReflectionException(String.format("Failed to instantiate class [%s].", clazz.getCanonicalName()));
        }
    }

    @SuppressWarnings("java:S3011")
    public static Method makeAccessible(Method method, Object object) {
        if (!method.canAccess(object)) {
            method.setAccessible(true);
        }
        return method;
    }

    public static Object invokeMethod(Method method, Object target, Object... args) throws ReflectionException {
        Preconditions.notNull(method, "Method must not be null");
        Preconditions.condition(
                (target != null),
                String.format("Cannot invoke non-static method [%s] on a null target.", method.toGenericString()));

        try {
            return makeAccessible(method, target).invoke(target, args);
        } catch (Exception t) {
            throw new ReflectionException(String.format("Failed to invoke method [%s].", method.toGenericString()));
        }
    }
}
