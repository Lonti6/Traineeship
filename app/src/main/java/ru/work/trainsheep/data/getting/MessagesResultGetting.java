package ru.work.trainsheep.data.getting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.work.trainsheep.send.ChatResult;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagesResultGetting {
    String status;
    ChatResult messages;
}
