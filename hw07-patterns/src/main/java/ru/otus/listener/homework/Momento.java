package ru.otus.listener.homework;

import ru.otus.model.Message;

public record Momento(Message message) {

    public Momento(Message message) {
        this.message = message.copy();
    }
}
