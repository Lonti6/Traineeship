package ru.work.trainsheep.data.getting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.work.trainsheep.send.ChatBlock;
import ru.work.trainsheep.send.ChatResult;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResultGetting {
    String status;
    ChatResult messages;
}
