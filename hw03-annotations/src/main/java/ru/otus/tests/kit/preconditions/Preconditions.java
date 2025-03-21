package ru.otus.tests.kit.preconditions;

import java.util.Collection;

public class Preconditions {

    private Preconditions() {}

    public static <T extends Collection<?>> void notEmpty(T collection, String message)
            throws PreconditionViolationException {
        condition(collection != null && !collection.isEmpty(), message);
    }

    public static <T> void notNull(T object, String message) throws PreconditionViolationException {
        condition(object != null, message);
    }

    public static void condition(boolean predicate, String message) throws PreconditionViolationException {
        if (!predicate) {
            throw new PreconditionViolationException(message);
        }
    }
}
