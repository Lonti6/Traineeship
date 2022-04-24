package ru.work.trainsheep.send;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationData {

    private String name = "";
    private String lastName = "";
    private String email = "";
    private String password = "";
    private String isCompany = "false";

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
