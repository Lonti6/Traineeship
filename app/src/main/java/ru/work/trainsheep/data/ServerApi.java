package ru.work.trainsheep.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.work.trainsheep.data.getting.AdvertResultGetting;
import ru.work.trainsheep.send.AdvertRequest;
import ru.work.trainsheep.send.LoginResult;
import ru.work.trainsheep.send.UserRegistrationData;

public interface ServerApi {
    @POST("/register")
    Call<LoginResult> register(@Body UserRegistrationData user);

    @POST("/adverts")
    Call<AdvertResultGetting> adverts(@Body AdvertRequest request);
}
