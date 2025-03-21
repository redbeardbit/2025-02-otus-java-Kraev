package ru.otus.tests.examples.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.tests.kit.annotations.After;
import ru.otus.tests.kit.annotations.Before;
import ru.otus.tests.kit.annotations.DisplayName;
import ru.otus.tests.kit.annotations.Test;
import ru.otus.tests.kit.asserts.Asserts;

public class ExampleThreeTest {

    private static final Logger logger = LoggerFactory.getLogger(ExampleThreeTest.class);

    private int expected = 0;

    @Before
    private void beforeOne() {
        expected = 10;
    }

    @Test
    @DisplayName("ExampleThreeTest: Test integer on equal")
    public void testOne() {
        int actual = 10;
        Asserts.assertEquals(actual, expected);
    }

    @After
    public void afterOne() {
        expected = 0;
        logger.info("This is example of an after action for test ExampleThreeTest");
    }
}
