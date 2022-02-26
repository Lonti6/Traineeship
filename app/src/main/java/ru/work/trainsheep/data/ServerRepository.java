package ru.work.trainsheep.data;

import android.app.Activity;
import android.content.Context;
import ru.work.trainsheep.AdvertRequest;
import ru.work.trainsheep.AdvertResult;
import ru.work.trainsheep.LoginResult;
import ru.work.trainsheep.UserRegistrationData;

import java.util.function.Consumer;

public interface ServerRepository {

    void register(UserRegistrationData user, Consumer<Result<UserRegistrationData>> callback);

    void login(UserRegistrationData user, Consumer<Result<UserRegistrationData>> callback);

    void getAdverts(AdvertRequest request, Consumer<Result<AdvertResult>> callback);

}
