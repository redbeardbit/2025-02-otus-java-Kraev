package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        TypeToken<List<Measurement>> collectionType = new TypeToken<>() {};
        List<Measurement> result = new ArrayList<>();

        try (FileReader fileReader = new FileReader(
                ResourcesFileLoader.class.getClassLoader().getResource(fileName).getFile())) {
            JsonReader jsonReader = new JsonReader(fileReader);
            result = new Gson().fromJson(jsonReader, collectionType);
        } catch (Exception e) {
            throw new FileProcessException("Error reading from file", e);
        }

        return result;
    }
}
