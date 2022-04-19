package ru.work.trainsheep.send;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyRequest {

    // use is empty for all tags
    List<String> tags = new ArrayList<>();
    String text;

    int page = 0;
    int countNotesOnPage = 10;

}
