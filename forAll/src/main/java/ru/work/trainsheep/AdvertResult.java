package ru.work.trainsheep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class AdvertResult {

    @NonNull
    ArrayList<Note> notes;
    int numberPage = 1;
    int allNotes;
    int countNotesInPage;
}
