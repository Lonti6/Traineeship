package ru.work.trainsheep.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.work.trainsheep.data.getting.ChatsResultGetting;
import ru.work.trainsheep.data.getting.CompanyResultGetting;
import ru.work.trainsheep.data.getting.MessageResultGetting;
import ru.work.trainsheep.data.getting.VacancyResultGetting;
import ru.work.trainsheep.send.*;

import java.util.List;

public interface ServerApi {
    @POST("/register")
    Call<LoginResult> register(@Body UserRegistrationData user);

    @POST("/login")
    Call<LoginResult> login(@Header("Authorization") String credentials);

    @POST("/adverts")
    Call<VacancyResultGetting> adverts(@Body VacancyRequest request);

    @POST("/companies")
    Call<CompanyResultGetting> companies(@Body CompanyRequest request);

    @POST("/chats")
    Call<ChatsResultGetting> chats(@Header("Authorization") String credentials);

    @POST("/messages")
    Call<MessageResultGetting> messages(@Body ChatRequest request, @Header("Authorization") String credentials);

}
