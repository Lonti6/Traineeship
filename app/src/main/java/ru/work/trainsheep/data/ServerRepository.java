package ru.work.trainsheep.data;

import android.app.Activity;
import android.content.Context;
import ru.work.trainsheep.LoginResult;
import ru.work.trainsheep.UserRegistrationData;

import java.util.function.Consumer;

public interface ServerRepository {

    void register(Activity activity, UserRegistrationData user, Consumer<Result<LoginResult>> callback);

}
