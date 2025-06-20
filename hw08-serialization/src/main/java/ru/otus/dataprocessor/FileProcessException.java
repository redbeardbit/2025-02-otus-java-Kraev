package ru.otus.dataprocessor;

public class FileProcessException extends RuntimeException {

    public FileProcessException(String msg, Exception e) {
        super(msg, e);
    }
}
