package ru.work.trainsheep;


import java.util.Arrays;
import java.util.List;

public class Note {
    List<String> tags;
    String header;
    String content;
    String company;

    public Note(List<String> tags, String header, String content, String company) {
        this.tags = tags;
        this.header = header;
        this.content = content;
        this.company = company;
    }

    public Note(String header, String content, String company, String... tags) {
        this.header = header;
        this.content = content;
        this.company = company;
        this.tags = Arrays.asList(tags);
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Note{" +
                "tags=" + tags +
                ", header='" + header + '\'' +
                ", content='" + content + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
