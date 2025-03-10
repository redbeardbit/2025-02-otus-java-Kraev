package ru.otus.tests.kit.preconditions;

import java.util.Collection;

public class Preconditions {

    public Preconditions() {
    }

    public static <T extends Collection<?>> T notEmpty(T collection, String message)
            throws PreconditionViolationException {

        condition(collection != null && !collection.isEmpty(), message);
        return collection;
    }

    public static <T> T notNull(T object, String message) throws PreconditionViolationException {
        condition(object != null, message);
        return object;
    }

    public static void condition(boolean predicate, String message) throws PreconditionViolationException {
        if (!predicate) {
            throw new PreconditionViolationException(message);
        }
    }
}