package ru.work.trainsheep.send;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class CompanyResult {
    @NonNull
    List<CompanyNote> notes;
    int numberPage = 1;
    int allNotes;
    int countNotesInPage;
}
