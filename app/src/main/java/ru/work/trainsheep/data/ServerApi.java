package ru.work.trainsheep.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.work.trainsheep.LoginResult;
import ru.work.trainsheep.UserRegistrationData;

public interface ServerApi {
    @POST("/register")
    Call<LoginResult> register(@Body UserRegistrationData user);
}
