package ru.otus.listener.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.model.Message;

public class MessageKeeper {

    private final Map<Long, Momento> storage = new HashMap<>();

    public void putMessage(Message message) {
        storage.put(message.getId(), new Momento(message));
    }

    public Optional<Message> findMessageById(long id) {
        if (storage.containsKey(id)) {
            return Optional.of(storage.get(id).message());
        } else {
            return Optional.empty();
        }
    }
}
