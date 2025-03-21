package ru.otus.tests.examples.tests;

import ru.otus.tests.kit.annotations.Before;
import ru.otus.tests.kit.annotations.DisplayName;
import ru.otus.tests.kit.annotations.Test;
import ru.otus.tests.kit.asserts.Asserts;

public class ExampleOneTest {

    private int expected = 0;

    @Before
    private void beforeOne() {
        expected = 10;
    }

    @Test
    @DisplayName("ExampleOneTest: Test integer on equal")
    public void testOne() {
        int actual = 10;
        Asserts.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("ExampleOneTest: Test integer not equal")
    public void testTwo() {
        int actual = 1;
        Asserts.assertNotEquals(actual, expected);
    }
}
