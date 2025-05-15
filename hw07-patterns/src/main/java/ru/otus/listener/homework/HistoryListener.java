package ru.otus.listener.homework;

import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    MessageKeeper keeper = new MessageKeeper();

    @Override
    public void onUpdated(Message msg) {
        keeper.putMessage(msg);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return keeper.findMessageById(id);
    }
}
