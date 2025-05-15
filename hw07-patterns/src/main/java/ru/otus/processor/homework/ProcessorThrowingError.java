package ru.otus.processor.homework;

import java.time.LocalDateTime;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorThrowingError implements Processor {

    private LocalDateTime dateTime;

    public ProcessorThrowingError(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @SuppressWarnings("java:S112")
    @Override
    public Message process(Message message) {
        if (dateTime.getSecond() % 2 == 0) throw new RuntimeException("Exception \"Even second\"");
        return message;
    }
}
