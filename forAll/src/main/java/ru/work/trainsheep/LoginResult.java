package ru.work.trainsheep;

import java.io.Serializable;

public class LoginResult  {

    private String username = "";

    public LoginResult() {
    }

    public LoginResult(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
