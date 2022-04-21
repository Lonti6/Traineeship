package ru.work.trainsheep.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.work.trainsheep.data.getting.*;
import ru.work.trainsheep.send.*;

public interface ServerApi {
    @POST("/register")
    Call<LoginResult> register(@Body UserRegistrationData user);

    @POST("/login")
    Call<LoginResult> login(@Header("Authorization") String credentials);

    @POST("/vacanciesWithAuth")
    Call<VacancyResultGetting> vacancies(@Body VacancyRequest request, @Header("Authorization") String credentials);

    @POST("/favoriteVacancies")
    Call<VacancyResultGetting> favoriteVacancies(@Body VacancyRequest request, @Header("Authorization") String credentials);

    @POST("/setFavoriteVacancy")
    Call<SetFavoriteGetting> setFavoriteVacancy(@Body SetFavoriteVacancyRequest request, @Header("Authorization") String credentials);

    @POST("/companies")
    Call<CompanyResultGetting> companies(@Body CompanyRequest request);

    @POST("/chats")
    Call<ChatsResultGetting> chats(@Header("Authorization") String credentials);

    @POST("/messages")
    Call<MessagesResultGetting> messages(@Body ChatRequest request, @Header("Authorization") String credentials);

    @POST("/send-message")
    Call<SendMessageGetting> sendMessage(@Body SendMessageRequest request, @Header("Authorization") String credentials);

    @POST("/createVacancy")
    Call<CreateVacancyGetting> createVacancy(@Body VacancyNote request, @Header("Authorization") String credentials);

    @POST("/search-chats")
    Call<ChatsResultGetting> searchChats(@Body SearchChatsRequest request, @Header("Authorization") String credentials);


}
