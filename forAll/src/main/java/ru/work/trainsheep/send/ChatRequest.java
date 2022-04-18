package ru.work.trainsheep.send;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    @NonNull
    String email;
    int page = 0;
    int countMessageOnPage = 20;
}
