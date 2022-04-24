package ru.work.trainsheep.send;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyRequest {

    // use is empty for all tags
    List<String> tags = new ArrayList<>();
    @NonNull
    String text = "";

    String emailFilter = "all";

    int page = 0;
    int countNotesOnPage = 10;

}
