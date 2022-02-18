package ru.work.trainsheep;



public class UserRegistrationDto {
    String email;
    String password;


    public UserRegistrationDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserRegistrationDto() {
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
