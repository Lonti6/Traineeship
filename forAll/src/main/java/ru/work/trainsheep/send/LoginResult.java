package ru.work.trainsheep.send;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult  {
    private String status;
    private UserRegistrationData user;
}
