package ru.work.trainsheep.send;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class AdvertResult {

    @NonNull
    List<Note> notes;
    int numberPage = 1;
    int allNotes;
    int countNotesInPage;
}
