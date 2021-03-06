package ru.work.trainsheep.send;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData implements Serializable {

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
    private String isCompany = "false";

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

    public boolean isCompany(){
        return isCompany.equals("true");
    }

    public void setCompany(boolean c){
        if (c)
            isCompany = "true";
        else
            isCompany = "false";
    }
}
