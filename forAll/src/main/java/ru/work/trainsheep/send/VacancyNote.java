package ru.work.trainsheep.send;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyNote implements Serializable {
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


}
