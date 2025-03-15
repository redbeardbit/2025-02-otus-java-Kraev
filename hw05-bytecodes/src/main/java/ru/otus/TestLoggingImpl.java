package ru.otus;

import ru.otus.annotation.Log;

public class TestLoggingImpl implements TestLogging {
    @Log
    @Override
    public void calculation(int param1) {
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
    }

    @Override
    public void calculation(int param1, int param2, int param3, int param4) {
    }
}
