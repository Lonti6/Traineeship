package ru.work.trainsheep.send;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

    private String firstName = "";
    private String lastName = "";
    private String patronymic = "";

    private long birthdate;
    private long registrationDate;
    @NonNull
    private String email = "";
    private String phoneNumber = "";
    private String avatarSrc = "";
    private List<String> competencies = new ArrayList<>();
    private boolean isCompany = false;

    private String university = "";
    private String specialization = "";
    private String city = "";
    private String description = "";

    private int curs;

    public void setBirthdate(Date date){
        birthdate = date.getTime();
    }

    public Date getBirthdate(){
        return new Date(birthdate);
    }

    public Date getRegistrationDate() {
        return new Date(registrationDate);
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate.getTime();
    }
}
