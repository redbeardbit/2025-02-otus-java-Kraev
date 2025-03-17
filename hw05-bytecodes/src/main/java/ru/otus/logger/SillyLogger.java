package ru.otus.logger;

public class SillyLogger implements Logger {
    @SuppressWarnings("java:S106")
    public void log(String var1) {
        System.out.println(var1);
    }
}
