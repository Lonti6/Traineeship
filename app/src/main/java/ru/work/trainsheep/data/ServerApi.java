package ru.work.trainsheep.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.work.trainsheep.data.getting.CompanyResultGetting;
import ru.work.trainsheep.data.getting.VacancyResultGetting;
import ru.work.trainsheep.send.CompanyRequest;
import ru.work.trainsheep.send.VacancyRequest;
import ru.work.trainsheep.send.LoginResult;
import ru.work.trainsheep.send.UserRegistrationData;

public interface ServerApi {
    @POST("/register")
    Call<LoginResult> register(@Body UserRegistrationData user);

    @POST("/login")
    Call<LoginResult> login(@Body UserRegistrationData user);

    @POST("/adverts")
    Call<VacancyResultGetting> adverts(@Body VacancyRequest request);

    @POST("/companies")
    Call<CompanyResultGetting> companies(@Body CompanyRequest request);
}
