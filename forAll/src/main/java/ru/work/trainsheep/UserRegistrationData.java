package ru.work.trainsheep;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserRegistrationData {
    @NonNull
    String email;
    @NonNull
    String password;
}
