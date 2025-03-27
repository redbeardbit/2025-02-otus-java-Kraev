package ru.otus.dataprocessor;

import java.io.IOException;

public class FileProcessException extends RuntimeException {

    public FileProcessException(String msg, IOException e) {
        super(msg, e);
    }
}
