package ru.otus.tests.examples;

import ru.otus.tests.examples.tests.ExampleOneTest;
import ru.otus.tests.examples.tests.ExampleThreeTest;
import ru.otus.tests.examples.tests.ExampleTwoTest;
import ru.otus.tests.kit.engine.TestExecutor;

public class Main {

    public static void main(String[] args) {
        TestExecutor testExecutor = new TestExecutor();
        testExecutor.run(ExampleOneTest.class, ExampleTwoTest.class, ExampleThreeTest.class);
    }
}
