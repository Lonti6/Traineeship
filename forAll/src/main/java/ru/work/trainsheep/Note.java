package ru.work.trainsheep;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class Note {
    List<String> tags;
    String header;
    String content;
    String company;

    public Note(String header, String content, String company, String... tags) {
        this.header = header;
        this.content = content;
        this.company = company;
        this.tags = Arrays.asList(tags);
    }


    @Override
    public String toString() {
        return "Note{\n" +
                "tags=" + tags +
                ", \n    header='" + header + '\'' +
                ", \n    content='" + content + '\'' +
                ", \n    company='" + company + '\'' +
                "\n}\n";
    }
}
