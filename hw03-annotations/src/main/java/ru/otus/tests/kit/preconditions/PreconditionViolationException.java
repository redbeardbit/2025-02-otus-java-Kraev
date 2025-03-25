package ru.otus.tests.kit.preconditions;

public class PreconditionViolationException extends RuntimeException {

    public PreconditionViolationException(String message) {
        super(message);
    }
}
