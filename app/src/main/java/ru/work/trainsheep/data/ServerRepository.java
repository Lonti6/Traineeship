package ru.work.trainsheep.data;

import android.app.Activity;
import android.content.Context;
import ru.work.trainsheep.*;

import java.util.List;
import java.util.function.Consumer;

public interface ServerRepository {

    void register(UserRegistrationData user, Consumer<Result<UserRegistrationData>> callback);

    void login(UserRegistrationData user, Consumer<Result<UserRegistrationData>> callback);

    void getAdverts(AdvertRequest request, Consumer<Result<AdvertResult>> callback);

    void getChats(Consumer<Result<List<ChatBlock>>> callback);

    void getMessages(ChatRequest request, Consumer<Result<ChatResult>> callback);

}
