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

    private Date birthdate = new Date();
    private Date registrationDate = new Date();
    @NonNull
    private String email = "";
    private String phoneNumber = "";
    private String avatarSrc = "";
    private ArrayList<String> competencies = new ArrayList<>();
    private boolean isCompany = false;

    private String university = "";
    private String specialization = "";
    private String city = "";
    private String description = "";

    private int year; //год обучения
    private int curs;

}
