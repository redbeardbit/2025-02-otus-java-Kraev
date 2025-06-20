package ru.otus.jdbc.mapper;

import java.util.function.Function;

public interface Cache<K, V> {

    V get(K key);

    void put(K key, V value);

    V computeIfAbsent(K key, Function<K, V> function);
}
