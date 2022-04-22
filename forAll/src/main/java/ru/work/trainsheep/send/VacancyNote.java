package ru.work.trainsheep.send;


import lombok.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class VacancyNote implements Serializable {

    @NonNull
    List<String> tags;
    @NonNull
    String header;
    @NonNull
    String content;
    @NonNull
    String company;
    @NonNull
    String salary;
    @NonNull
    boolean favorite;
    @NonNull
    long id; // нужен для редактирования конкретной вакансии на сервере
    @NonNull
    String email;
    String imageSrc = "";
    String city = "Ёбург";
    int workingHours;
    boolean furtherCooperation = true;
    boolean contractualSalary = true;
    boolean distanceWork = true;


    public VacancyNote(String header, String content, String company, String salary,
                       boolean furtherCooperation, boolean contractualSalary,
                       boolean distanceBox, int workingHours, int id, String... tags) {
        this.header = header;
        this.content = content;
        this.company = company;
        this.salary = salary;
        this.tags = Arrays.asList(tags);
        this.furtherCooperation = furtherCooperation;
        this.workingHours = workingHours;
        this.contractualSalary = contractualSalary;
        this.distanceWork = distanceBox;
        this.id = id;
    }
}
