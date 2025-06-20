package ru.otus;

import ru.otus.proxy.Ioc;

public class Demo {
    public void action() {

        TestLogging testLogging = Ioc.newLoggedInstance(new TestLoggingImpl(), TestLogging.class);

        assert testLogging != null;

        testLogging.calculation(1);
        testLogging.calculation(2, 3);
        testLogging.calculation(4, 5, "test");
        testLogging.calculation(4, 5, 6, 7);
    }
}
