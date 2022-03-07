package ru.work.trainsheep.data;

import android.os.Handler;
import android.os.Looper;
import lombok.val;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.work.trainsheep.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class RealServerRepository implements ServerRepository{

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ServerApi api = retrofit.create(ServerApi.class);
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    UserRegistrationData saveUserData = null;

    @Override
    public void register(UserRegistrationData user, Consumer<Result<UserRegistrationData>> callback) {
        executor.execute(() -> {
            val call = api.register(user);
            try {
                val response = call.execute();
                val loginResult = response.body();
                if (loginResult != null && Objects.equals(loginResult.getStatus(), "ok")) {
                    saveUserData = loginResult.getUser();
                    handler.post(() -> callback.accept(Result.success(saveUserData)));
                } else
                    handler.post(() -> callback.accept(Result.error(new Exception("status fail"))));

            } catch (IOException e) {
                handler.post(() -> callback.accept(Result.error(e)));
                System.err.println(e.getMessage());
            }
        });
    }

    @Override
    public void getChats(Consumer<Result<List<ChatBlock>>> callback) {

    }

    @Override
    public void getMessages(ChatRequest request, Consumer<Result<ChatResult>> callback) {

    }

    @Override
    public void login(UserRegistrationData user, Consumer<Result<UserRegistrationData>> callback) {
        //TODO
    }

    @Override
    public void getAdverts(AdvertRequest request, Consumer<Result<AdvertResult>> callback) {
        //TODO
    }
}
