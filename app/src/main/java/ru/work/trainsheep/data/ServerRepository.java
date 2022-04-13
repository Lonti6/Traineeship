package ru.work.trainsheep.data;

import ru.work.trainsheep.send.*;

import java.util.List;
import java.util.function.Consumer;

public abstract class ServerRepository {

    public abstract void register(UserRegistrationData user, Consumer<UserRegistrationData> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void login(UserRegistrationData user, Consumer<UserRegistrationData> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void getVacancys(VacancyRequest request, Consumer<VacancyResult> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void getCompanys(CompanyRequest request, Consumer<CompanyResult> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void getChats(Consumer<List<ChatBlock>>callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract void getMessages(ChatRequest request, Consumer<ChatResult> callbackSuccess, Consumer<Exception> callbackFailure);

    public abstract boolean isLogin();


    public void register(UserRegistrationData user, Consumer<UserRegistrationData> callbackSuccess){
        register(user, callbackSuccess, Throwable::printStackTrace);
    }

    public void login(UserRegistrationData user, Consumer<UserRegistrationData> callbackSuccess){
        login(user, callbackSuccess, Throwable::printStackTrace);
    }

    public void getVacancys(VacancyRequest request, Consumer<VacancyResult> callbackSuccess){
        getVacancys(request, callbackSuccess, Throwable::printStackTrace);
    }

    public void getCompanys(CompanyRequest request, Consumer<CompanyResult> callbackSuccess){
        getCompanys(request, callbackSuccess, Throwable::printStackTrace);
    }

    public void getChats(Consumer<List<ChatBlock>>callbackSuccess){
        getChats(callbackSuccess, Throwable::printStackTrace);
    }

    public void getMessages(ChatRequest request, Consumer<ChatResult> callbackSuccess){
        getMessages(request, callbackSuccess, Throwable::printStackTrace);
    }


}
