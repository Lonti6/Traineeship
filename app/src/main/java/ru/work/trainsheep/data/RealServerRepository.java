package ru.work.trainsheep.data;

import android.os.Handler;
import android.os.Looper;
import lombok.val;
import okhttp3.Credentials;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.work.trainsheep.data.getting.CreateVacancyGetting;
import ru.work.trainsheep.send.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class RealServerRepository extends ServerRepository{

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ServerRepositoryFactory.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ServerApi api = retrofit.create(ServerApi.class);
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());


    @Override
    public void register(UserRegistrationData user, Consumer<String> callbackSuccess, Consumer<Exception> callbackFailure) {
        executor.execute(() -> {

            val call = api.register(user);
            try {
                val response = call.execute();
                val loginResult = response.body();
                if (loginResult != null && Objects.equals(loginResult.getStatus(), "ok")) {
                    handler.post(() -> callbackSuccess.accept(loginResult.getName()));
                } else
                    handler.post(() -> callbackFailure.accept(new Exception("status fail")));

            } catch (IOException e) {
                handler.post(() -> callbackFailure.accept(e));
                System.err.println(e.getMessage());
            }
        });
    }
    private String getCredentials(){
        val info = UserInfo.getInstance().getRegistrationData();
        return Credentials.basic(info.getEmail(), info.getPassword());
    }

    @Override
    public void login(UserRegistrationData user, Consumer<String> callbackSuccess, Consumer<Exception> callbackFailure) {
        val info = UserInfo.getInstance().getRegistrationData();
        info.setEmail(user.getEmail());
        info.setPassword(user.getPassword());
        executor.execute(() -> {

            val call = api.login(getCredentials());
            try {
                val response = call.execute();

                val loginResult = response.body();
                if (loginResult != null && Objects.equals(loginResult.getStatus(), "ok")) {
                    handler.post(() -> callbackSuccess.accept(loginResult.getName()));
                } else
                    handler.post(() -> callbackFailure.accept(new Exception("status fail")));

            } catch (IOException e) {
                handler.post(() -> callbackFailure.accept(e));
                System.err.println(e.getMessage());
            }
        });
    }

    @Override
    public void getVacancies(VacancyRequest request, Consumer<VacancyResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        executor.execute(() -> {
            val call = api.vacancies(request, getCredentials());
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
    public void getFavoriteVacancies(VacancyRequest request, Consumer<VacancyResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        executor.execute(() -> {
            val call = api.favoriteVacancies(request, getCredentials());
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
    public void setFavoriteVacancy(SetFavoriteVacancyRequest request, Consumer<VacancyNote> callbackSuccess, Consumer<Exception> callbackFailure) {
        executor.execute(() -> {
            val call = api.setFavoriteVacancy(request, getCredentials());
            try {
                val response = call.execute();
                val result = response.body();
                if (result != null && Objects.equals(result.getStatus(), "ok")) {
                    handler.post(() -> callbackSuccess.accept(result.getVacancy()));
                } else
                    handler.post(() -> callbackFailure.accept(new Exception("status fail")));

            } catch (IOException e) {
                handler.post(() -> callbackFailure.accept(e));
                System.err.println(e.getMessage());
            }
        });
    }

    @Override
    public void getCompanies(CompanyRequest request, Consumer<CompanyResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        executor.execute(() -> {
            val call = api.companies(request);
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
        executor.execute(() -> {

            val call = api.chats(getCredentials());

            try {
                val response = call.execute();

                val result = response.body();
                if (result != null && Objects.equals(result.getStatus(), "ok")) {
                    handler.post(() -> callbackSuccess.accept(result.getChats()));
                } else
                    handler.post(() -> callbackFailure.accept(new Exception("status fail")));

            } catch (IOException e) {
                handler.post(() -> callbackFailure.accept(e));
                System.err.println(e.getMessage());
            }
        });
    }

    @Override
    public void getMessages(ChatRequest request, Consumer<ChatResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        executor.execute(() -> {

            val call = api.messages(request, getCredentials());

            try {
                val response = call.execute();

                val result = response.body();
                if (result != null && Objects.equals(result.getStatus(), "ok")) {
                    handler.post(() -> callbackSuccess.accept(result.getMessages()));
                } else
                    handler.post(() -> callbackFailure.accept(new Exception("status fail")));

            } catch (IOException e) {
                handler.post(() -> callbackFailure.accept(e));
                System.err.println(e.getMessage());
            }
        });
    }

    @Override
    public void getSendMessage(SendMessageRequest request, Consumer<ChatMessage> callbackSuccess, Consumer<Exception> callbackFailure) {
        executor.execute(() -> {

            val call = api.sendMessage(request, getCredentials());

            try {
                val response = call.execute();

                val result = response.body();
                if (result != null && Objects.equals(result.getStatus(), "ok")) {
                    handler.post(() -> callbackSuccess.accept(result.getMessage()));
                } else
                    handler.post(() -> callbackFailure.accept(new Exception("status fail")));

            } catch (IOException e) {
                handler.post(() -> callbackFailure.accept(e));
                System.err.println(e.getMessage());
            }
        });
    }

    @Override
    public void createVacancy(VacancyNote request, Consumer<VacancyNote> callbackSuccess, Consumer<Exception> callbackFailure) {
        executor.execute(() -> {

            val call = api.createVacancy(request, getCredentials());

            try {
                val response = call.execute();

                val result = response.body();
                if (result != null && Objects.equals(result.getStatus(), "ok")) {
                    handler.post(() -> callbackSuccess.accept(result.getVacancy()));
                } else
                    handler.post(() -> callbackFailure.accept(new Exception("status fail")));

            } catch (IOException e) {
                handler.post(() -> callbackFailure.accept(e));
                System.err.println(e.getMessage());
            }
        });
    }

    @Override
    public boolean isLogin() {
        return !UserInfo.getInstance().getRegistrationData().getEmail().equals("");
    }
}

