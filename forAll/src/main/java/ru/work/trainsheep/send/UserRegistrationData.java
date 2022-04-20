package ru.work.trainsheep.send;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationData {

    private String name = "";
    private String email = "";
    private String password = "";
    private boolean isCompany = false;
}
