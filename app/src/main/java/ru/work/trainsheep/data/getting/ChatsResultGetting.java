package ru.work.trainsheep.data.getting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.work.trainsheep.send.ChatBlock;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatsResultGetting {
    String status;
    List<ChatBlock> chats;
}
