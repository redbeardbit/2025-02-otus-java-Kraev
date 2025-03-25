package ru.otus.tests.examples.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.tests.kit.annotations.After;
import ru.otus.tests.kit.annotations.DisplayName;
import ru.otus.tests.kit.annotations.Test;
import ru.otus.tests.kit.asserts.Asserts;

public class ExampleTwoTest {

    private static final Logger logger = LoggerFactory.getLogger(ExampleTwoTest.class);

    private int expected = 0;

    @Test
    @DisplayName("ExampleTwoTest: Test integer on equal")
    public void testOne() {
        int actual = 10;
        Asserts.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("ExampleTwoTest: Test integer not equal")
    public void testTwo() {
        int actual = 1;
        Asserts.assertNotEquals(actual, expected);
    }

    @After
    public void afterTest() {
        expected = 0;
        logger.info("This is example of an after action for test ExampleTwoTest");
    }

    @After
    public void afterTestAdditional() {
        expected = 0;
        logger.info("This is example of additional after action for test ExampleTwoTest");
    }
}
