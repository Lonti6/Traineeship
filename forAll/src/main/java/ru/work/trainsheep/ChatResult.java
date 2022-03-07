package ru.work.trainsheep;

import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResult {
    @NonNull
    List<ChatMessage> messages;
    int page;
    int countAllMessages;
    String name;
    String icon;
}
