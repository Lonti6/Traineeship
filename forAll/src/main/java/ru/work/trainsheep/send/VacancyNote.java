package ru.work.trainsheep.send;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class VacancyNote {
    List<String> tags;
    String header;
    String content;
    String company;
    String salary;
    boolean favorite;
    long id;

    public VacancyNote(String header, String content, String company, String salary, String... tags) {
        this.header = header;
        this.content = content;
        this.company = company;
        this.salary = salary;
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
