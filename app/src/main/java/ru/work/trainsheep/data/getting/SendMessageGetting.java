package ru.work.trainsheep.data.getting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.work.trainsheep.send.ChatMessage;
import ru.work.trainsheep.send.ChatResult;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageGetting {
    String status;
    ChatMessage message;
}
