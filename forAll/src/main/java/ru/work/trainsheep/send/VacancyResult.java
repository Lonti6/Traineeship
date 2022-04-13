package ru.work.trainsheep.send;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public class VacancyResult {

    @NonNull
    List<VacancyNote> notes;
    int numberPage = 1;
    int allNotes;
    int countNotesInPage;
}
