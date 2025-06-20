package ru.otus.processor.homework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

class ProcessorThrowingErrorTest {

    @Test
    @DisplayName("Тестируем исключения на четной секунде")
    void process_evenSecond_throwError() {
        var message = new Message.Builder(1L).field1("field1").build();

        for (int i = 0; i < 100; i++) {
            var errorProcessor = new ProcessorThrowingError(LocalDateTime.ofEpochSecond(1000L, 0, ZoneOffset.UTC));
            assertThatThrownBy(() -> errorProcessor.process(message)).hasMessage("Exception \"Even second\"");
        }
    }

    @Test
    @DisplayName("Тестируем отсутствие исключения на нечетной секунде")
    void process_oddSecond_returnMessage() {
        var message = new Message.Builder(1L).field1("field1").build();

        for (int i = 0; i < 100; i++) {
            var errorProcessor = new ProcessorThrowingError(LocalDateTime.ofEpochSecond(1001L, 0, ZoneOffset.UTC));
            assertThat(errorProcessor.process(message)).isEqualTo(message);
        }
    }
}
