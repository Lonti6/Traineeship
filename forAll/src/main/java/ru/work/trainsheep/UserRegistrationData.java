package ru.work.trainsheep;


import java.io.Serializable;

public class UserRegistrationData {
    String email;
    String password;


    public UserRegistrationData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserRegistrationData() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
