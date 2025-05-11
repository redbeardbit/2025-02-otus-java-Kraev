package ru.otus.property;

import com.google.common.base.Preconditions;

public class ApplicationConfig implements Config {

    private final PropertyReader propertyReader;

    private int cellsCount;

    private int cellsCapacity;

    private int[] availableNominal;

    public ApplicationConfig(PropertyReader propertyReader) {
        Preconditions.checkNotNull(propertyReader, "propertyReader can't be null");
        this.propertyReader = propertyReader;
    }

    public void init() {
        this.cellsCount = propertyReader.getIntProperty("cells-count");
        this.cellsCapacity = propertyReader.getIntProperty("cell-capacity");
        this.availableNominal = propertyReader.getIntArrayProperty("available-nominal");
    }

    public int[] getAvailableNominal() {
        return availableNominal;
    }

    public int getCellsCapacity() {
        return cellsCapacity;
    }

    public int getCellsCount() {
        return cellsCount;
    }
}
