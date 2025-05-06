package ru.otus;

import com.google.common.base.Joiner;

@SuppressWarnings("java:S106")
public class HelloOtus {

    private static final String[] DEFAULT_WORDS = {"Hello", "otus", "!"};

    public static void main(String[] args) {
        sayHello(DEFAULT_WORDS);
    }

    @SuppressWarnings("java:S106")
    private static void sayHello(String... values) {
        Joiner joiner = Joiner.on(" ").skipNulls();
        System.out.println(joiner.join(values));
    }
}
