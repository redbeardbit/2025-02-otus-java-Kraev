package ru.otus.dataprocessor;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            if (!data.isEmpty()) {
                new Gson().toJson(getSortedData(data), fileWriter);
            }
        } catch (IOException e) {
            throw new FileProcessException("Error writing to file", e);
        }
    }

    private static LinkedHashMap<String, Double> getSortedData(Map<String, Double> data) {
        return data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
