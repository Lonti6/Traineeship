package ru.work.trainsheep.send;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertRequest {

    // use is empty for all tags
    ArrayList<String> tags = new ArrayList<>();

    int page = 1;
    int countNotesOnPage = 10;

}
