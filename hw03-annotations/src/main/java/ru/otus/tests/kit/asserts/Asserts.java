package ru.otus.tests.kit.asserts;

public class Asserts {

    private Asserts() {}

    public static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            failNotEqual(expected, actual);
        }
    }

    public static void assertNotEquals(int unexpected, int actual) {
        if (unexpected == actual) {
            failEqual(unexpected, actual);
        }
    }

    private static void failNotEqual(Object expected, Object actual) {
        String message =
                String.format("Expected value {%s}, actual value {%s}", expected.toString(), actual.toString());
        throw new AssertionException(message);
    }

    private static void failEqual(Object expected, Object actual) {
        String message = String.format(
                "Expected value different from {%s}, actual value {%s}", expected.toString(), actual.toString());
        throw new AssertionException(message);
    }
}
