package ru.otus.jdbc.mapper;

import static java.util.stream.Collectors.toCollection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import ru.otus.crm.annotation.Id;

@AllArgsConstructor
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> entityClass;
    private final Cache<Class<?>, List<Field>> cache;

    public static List<Field> getDeclaredFields(Class<?> clazz) {
        Objects.requireNonNull(clazz);
        return Arrays.stream(clazz.getDeclaredFields()).collect(toCollection(ArrayList::new));
    }

    @Override
    public String getName() {
        return entityClass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return entityClass.getDeclaredConstructor();
        } catch (NoSuchMethodException | SecurityException e) {
            return null;
        }
    }

    @Override
    public Field getIdField() {
        return getAllFields().stream()
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Field> getAllFields() {
        return cache.computeIfAbsent(entityClass, clazz -> getDeclaredFields(entityClass));
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields().stream().filter(f -> !f.equals(getIdField())).toList();
    }
}
