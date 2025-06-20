package ru.otus.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyReader {

    private static final Logger logger = LoggerFactory.getLogger(PropertyReader.class);

    private Properties properties;

    public PropertyReader() {
        properties = new Properties();
        try (InputStream is = PropertyReader.class.getResourceAsStream("/config.properties")) {
            properties.load(is);
        } catch (IOException e) {
            logger.error("error while loading properties", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean((getProperty(key)));
    }

    public int[] getIntArrayProperty(String key) {

        String value = getProperty(key);
        if (value.isEmpty()) {
            return new int[0];
        }
        return Arrays.stream(value.substring(1, value.length() - 1).split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
