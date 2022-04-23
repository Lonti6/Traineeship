package ru.work.trainsheep.data;

import android.content.Context;

import ru.work.trainsheep.data.getting.CreateVacancyGetting;
import ru.work.trainsheep.send.*;

import java.util.List;
import java.util.function.Consumer;

public abstract class ServerRepository {

    public void setApplicationContext(Context context) {
    }

    ;

    public abstract void register(UserRegistrationData user, Consumer<String> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void login(UserRegistrationData user, Consumer<String> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void getVacancies(VacancyRequest request, Consumer<VacancyResult> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void getFavoriteVacancies(VacancyRequest request, Consumer<VacancyResult> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void setFavoriteVacancy(SetFavoriteVacancyRequest request, Consumer<VacancyNote> callbackSuccess, Consumer<Exception> callbackFailure);


    public abstract void getCompanies(CompanyRequest request, Consumer<CompanyResult> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void getChats(Consumer<List<ChatBlock>> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void getMessages(ChatRequest request, Consumer<ChatResult> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void getSendMessage(SendMessageRequest request, Consumer<ChatMessage> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void createVacancy(VacancyNote request, Consumer<VacancyNote> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void removeVacancy(VacancyNote request, Consumer<String> callbackSuccess, Consumer<Exception> callbackFailure);


    public abstract void searchChats(SearchChatsRequest request, Consumer<List<ChatBlock>> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void sendUser(UserData request, Consumer<UserData> callbackSuccess, Consumer<Exception> callbackFailure);


    public void register(UserRegistrationData user, Consumer<String> callbackSuccess) {
        register(user, callbackSuccess, Throwable::printStackTrace);
    }

    public void login(UserRegistrationData user, Consumer<String> callbackSuccess) {
        login(user, callbackSuccess, Throwable::printStackTrace);
    }

    public void getVacancies(VacancyRequest request, Consumer<VacancyResult> callbackSuccess) {
        getVacancies(request, callbackSuccess, Throwable::printStackTrace);
    }

    public void getCompanies(CompanyRequest request, Consumer<CompanyResult> callbackSuccess) {
        getCompanies(request, callbackSuccess, Throwable::printStackTrace);
    }

    public void getChats(Consumer<List<ChatBlock>> callbackSuccess) {
        getChats(callbackSuccess, Throwable::printStackTrace);
    }

    public void getMessages(ChatRequest request, Consumer<ChatResult> callbackSuccess) {
        getMessages(request, callbackSuccess, Throwable::printStackTrace);
    }

    public void getSendMessage(SendMessageRequest request, Consumer<ChatMessage> callbackSuccess) {
        getSendMessage(request, callbackSuccess, Throwable::printStackTrace);
    }

    public void getFavoriteVacancies(VacancyRequest request, Consumer<VacancyResult> callbackSuccess) {
        getFavoriteVacancies(request, callbackSuccess, Throwable::printStackTrace);
    }


    public void setFavoriteVacancy(SetFavoriteVacancyRequest request, Consumer<VacancyNote> callbackSuccess) {
        setFavoriteVacancy(request, callbackSuccess, Throwable::printStackTrace);
    }

    public void createVacancy(VacancyNote request, Consumer<VacancyNote> callbackSuccess) {
        createVacancy(request, callbackSuccess, Throwable::printStackTrace);
    }

    public void searchChats(SearchChatsRequest request, Consumer<List<ChatBlock>> callbackSuccess) {
        searchChats(request, callbackSuccess, Throwable::printStackTrace);
    }

    public void sendUser(UserData request, Consumer<UserData> callbackSuccess) {
        sendUser(request, callbackSuccess, Throwable::printStackTrace);
    }

    public void removeVacancy(VacancyNote request, Consumer<String> callbackSuccess){
        removeVacancy(request, callbackSuccess, Throwable::printStackTrace);
    }

}
