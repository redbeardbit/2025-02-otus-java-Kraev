package ru.otus.cache;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;

public record MethodType(String methodName, Parameter[] parameters) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodType that = (MethodType) o;
        return Objects.equals(methodName, that.methodName) && Arrays.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(methodName, Arrays.stream(parameters).count());
        result = 31 * result + Arrays.hashCode(parameters);
        return result;
    }

    @Override
    public String toString() {
        return methodName;
    }
}
