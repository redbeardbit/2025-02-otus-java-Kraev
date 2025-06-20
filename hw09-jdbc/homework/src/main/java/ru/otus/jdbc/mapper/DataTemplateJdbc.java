package ru.otus.jdbc.mapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@SuppressWarnings({"java:S1068", "java:S3011"})
@Slf4j
@AllArgsConstructor
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;
    private final RowMapper<T> rowMapper;

    @Override
    public Optional<T> findById(Connection connection, long id) {
        log.debug(entitySQLMetaData.getSelectByIdSql());
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) return rowMapper.mapRow(rs);
            } catch (SQLException e) {
                log.error("Передан некорректный маппинг", e);
            }
            return null;
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        log.debug(entitySQLMetaData.getSelectAllSql());
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    try {
                        List<T> result = new ArrayList<>();
                        while (rs.next()) {
                            result.add(rowMapper.mapRow(rs));
                        }
                        return result;
                    } catch (SQLException e) {
                        log.error("Передан некорректный маппинг", e);
                        return null;
                    }
                })
                .orElse(Collections.emptyList());
    }

    @Override
    public long insert(Connection connection, T entity) {
        var parameters = new ArrayList<>();
        for (var field : entityClassMetaData.getFieldsWithoutId()) {
            field.setAccessible(true);
            try {
                parameters.add(field.get(entity));
            } catch (IllegalAccessException e) {
                log.error("Ошибка создания сущности", e);
            }
        }
        log.debug(entitySQLMetaData.getInsertSql());
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), parameters);
    }

    @Override
    public void update(Connection connection, T entity) {
        try {
            var parameters = new ArrayList<>();
            for (var field : entityClassMetaData.getAllFields()) {
                field.setAccessible(true);
                parameters.add(field.get(entity));
            }
            var idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            parameters.add(idField.get(entity));
            log.debug(entitySQLMetaData.getUpdateSql());
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), parameters);
        } catch (Exception e) {
            log.error("Ошибка обновления сущности", e);
        }
    }
}
