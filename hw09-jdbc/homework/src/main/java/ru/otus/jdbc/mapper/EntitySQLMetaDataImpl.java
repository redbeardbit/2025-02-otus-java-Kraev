package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entityClassMetaData;

    @Override
    public String getSelectAllSql() {
        return String.format(
                "select %s from %s",
                getFieldsString(entityClassMetaData.getAllFields()), entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(
                "select %s from %s where %s = ?",
                getFieldsString(entityClassMetaData.getAllFields()),
                entityClassMetaData.getName(),
                entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        return String.format(
                "insert into %s (%s) values (%s)",
                entityClassMetaData.getName(),
                getFieldsString(entityClassMetaData.getFieldsWithoutId()),
                getMarkersString(entityClassMetaData.getFieldsWithoutId()));
    }

    @Override
    public String getUpdateSql() {

        return String.format(
                "update %s set %s where %s = ?",
                entityClassMetaData.getName(),
                getFieldsForUpdateString(entityClassMetaData.getFieldsWithoutId()),
                entityClassMetaData.getIdField().getName());
    }

    private String getFieldsString(List<Field> fieldList) {
        return fieldList.stream().map(Field::getName).collect(Collectors.joining(","));
    }

    private String getMarkersString(List<Field> fieldList) {
        return IntStream.range(0, fieldList.size()).mapToObj(i -> "?").collect(Collectors.joining(","));
    }

    private String getFieldsForUpdateString(List<Field> fieldList) {
        return fieldList.stream().map(f -> f.getName() + " = ?").collect(Collectors.joining(","));
    }
}
