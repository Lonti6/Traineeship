package ru.work.trainsheep;

import java.util.ArrayList;

public class AdvertRequest {

    // use is empty for all tags
    ArrayList<String> tags = new ArrayList<>();

    int page = 1;
    int countNotesOnPage = 10;

    public AdvertRequest(ArrayList<String> tags, int page, int countNotesOnPage) {
        this.tags = tags;
        this.page = page;
        this.countNotesOnPage = countNotesOnPage;
    }

    public AdvertRequest(int page) {
        this.page = page;
    }

    public AdvertRequest(ArrayList<String> tags, int page) {
        this.tags = tags;
        this.page = page;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCountNotesOnPage() {
        return countNotesOnPage;
    }

    public void setCountNotesOnPage(int countNotesOnPage) {
        this.countNotesOnPage = countNotesOnPage;
    }

    @Override
    public String toString() {
        return "AdvertRequest{" +
                "tags=" + tags +
                ", page=" + page +
                ", countNotesOnPage=" + countNotesOnPage +
                '}';
    }
}
