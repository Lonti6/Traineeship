package ru.work.trainsheep;

import java.util.ArrayList;

public class AdvertResult {
    ArrayList<Note> notes;
    int numberPage;
    int allPages;
    int countNotesInPage;

    public AdvertResult(ArrayList<Note> notes, int numberPage, int allPages, int countNotesInPage) {
        this.notes = notes;
        this.numberPage = numberPage;
        this.allPages = allPages;
        this.countNotesInPage = countNotesInPage;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getCountNotesInPage() {
        return countNotesInPage;
    }

    public void setCountNotesInPage(int countNotesInPage) {
        this.countNotesInPage = countNotesInPage;
    }

    @Override
    public String toString() {
        return "AdvertResult{" +
                "notes=" + notes +
                ", numberPage=" + numberPage +
                ", allPages=" + allPages +
                ", countNotesInPage=" + countNotesInPage +
                '}';
    }
}
