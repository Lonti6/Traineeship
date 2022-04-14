package ru.work.trainsheep.send;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

    private String firstName = "";
    private String lastName = "";
    private String patronymic = "";
    private Date birthdate;
    private Date registrationDate;
    @NonNull
    private String email = "";
    private boolean isStudent = true;

    private String university = "";
    private String specialization = "";
    private int year; //год обучения

}
