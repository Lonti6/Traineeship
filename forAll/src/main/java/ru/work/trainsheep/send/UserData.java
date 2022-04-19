package ru.work.trainsheep.send;

import lombok.*;

import java.util.ArrayList;
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
    private String phoneNumber = "";
    private ArrayList<String> competencies;
    private boolean isCompany = false;

    private String university = "";
    private String specialization = "";
    private String city = "";
    private String description = "";

    private int year; //год обучения
    private int curs;

}
