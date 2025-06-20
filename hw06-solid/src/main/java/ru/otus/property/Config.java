package ru.otus.property;

public interface Config {

    int[] getAvailableNominal();

    int getCellsCapacity();

    int getCellsCount();

    void init();
}
