package ru.work.trainsheep.send;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class SearchChatsRequest {
    @NonNull
    private String text;
    private int page = 0;
    private int countChatsOnPage = 20;
}
