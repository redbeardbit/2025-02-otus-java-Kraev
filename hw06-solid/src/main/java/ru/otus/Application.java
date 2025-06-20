package ru.otus;

import ru.otus.engine.CashCellEngineImpl;
import ru.otus.property.ApplicationConfig;
import ru.otus.property.Config;
import ru.otus.property.PropertyReader;
import ru.otus.service.CashCellServiceImpl;

public class Application {

    public static void main(String[] args) {

        Config config = new ApplicationConfig(new PropertyReader());
        config.init();

        CashMachine cashMachine = new CashMachine(new CashCellEngineImpl(new CashCellServiceImpl(), config));

        cashMachine.run();
    }
}
