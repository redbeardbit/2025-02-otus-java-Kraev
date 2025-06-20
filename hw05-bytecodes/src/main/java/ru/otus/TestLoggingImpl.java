package ru.otus;

import ru.otus.annotation.Log;

public class TestLoggingImpl implements TestLogging {
    @Log
    @Override
    public void calculation(int param1) {
        // This method is empty because it is not yet implemented.
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        // This method is empty because it is not yet implemented.
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        // This method is empty because it is not yet implemented.
    }

    @Override
    public void calculation(int param1, int param2, int param3, int param4) {
        // This method is empty because it is not yet implemented.
    }
}
