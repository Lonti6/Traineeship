package ru.work.trainsheep.send;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserData {

    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthdate;
    private Date registrationDate;
    @NonNull
    private String email;
    private boolean isStudent;

    private String university;
    private String specialization;
    private int year; //год обучения

}
