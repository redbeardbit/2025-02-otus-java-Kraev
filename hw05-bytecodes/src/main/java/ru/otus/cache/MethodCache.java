package ru.otus.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("java:S6548")
public enum MethodCache {
    INSTANCE;

    private final Map<String, MethodType> cache = new HashMap<>();

    public Optional<MethodType> getMethod(String methodSignature) {
        return Optional.ofNullable(cache.get(methodSignature));
    }

    public void putMethod(String methodSignature, MethodType type) {
        cache.put(methodSignature, type);
    }
}
