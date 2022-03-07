package ru.work.trainsheep;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRequest {
    @NonNull
    String name;
    int page = 1;
    int countMessageOnPage = 20;
}