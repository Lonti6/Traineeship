package ru.work.trainsheep.data;

import android.os.Handler;
import android.os.Looper;
import lombok.val;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.work.trainsheep.send.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class RealServerRepository extends ServerRepository{

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.2.140:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ServerApi api = retrofit.create(ServerApi.class);
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    UserRegistrationData saveUserData = null;

    @Override
    public void register(UserRegistrationData user, Consumer<UserRegistrationData> callbackSuccess, Consumer<Exception> callbackFailure) {
        executor.execute(() -> {
            val call = api.register(user);
            try {
                val response = call.execute();
                val loginResult = response.body();
                if (loginResult != null && Objects.equals(loginResult.getStatus(), "ok")) {
                    saveUserData = loginResult.getUser();
                    handler.post(() -> callbackSuccess.accept(saveUserData));
                } else
                    handler.post(() -> callbackFailure.accept(new Exception("status fail")));

            } catch (IOException e) {
                handler.post(() -> callbackFailure.accept(e));
                System.err.println(e.getMessage());
            }
        });
    }

    @Override
    public void login(UserRegistrationData user, Consumer<UserRegistrationData> callbackSuccess, Consumer<Exception> callbackFailure) {

    }

    @Override
    public void getVacancys(VacancyRequest request, Consumer<VacancyResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        executor.execute(() -> {
            val call = api.adverts(request);
            try {
                val response = call.execute();
                val result = response.body();
                if (result != null && Objects.equals(result.getStatus(), "ok")) {
                    handler.post(() -> callbackSuccess.accept(result.getResult()));
                } else
                    handler.post(() -> callbackFailure.accept(new Exception("status fail")));

            } catch (IOException e) {
                handler.post(() -> callbackFailure.accept(e));
                System.err.println(e.getMessage());
            }
        });
    }

    @Override
    public void getCompanys(CompanyRequest request, Consumer<CompanyResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        executor.execute(() -> {
            val call = api.companys(request);
            try {
                val response = call.execute();
                val result = response.body();
                if (result != null && Objects.equals(result.getStatus(), "ok")) {
                    handler.post(() -> callbackSuccess.accept(result.getResult()));
                } else
                    handler.post(() -> callbackFailure.accept(new Exception("status fail")));

            } catch (IOException e) {
                handler.post(() -> callbackFailure.accept(e));
                System.err.println(e.getMessage());
            }
        });
    }

    @Override
    public void getChats(Consumer<List<ChatBlock>> callbackSuccess, Consumer<Exception> callbackFailure) {

    }

    @Override
    public void getMessages(ChatRequest request, Consumer<ChatResult> callbackSuccess, Consumer<Exception> callbackFailure) {

    }



    @Override
    public boolean isLogin() {
        return saveUserData != null;
    }
}

