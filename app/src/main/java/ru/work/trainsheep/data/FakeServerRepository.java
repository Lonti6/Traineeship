package ru.work.trainsheep.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import lombok.NonNull;
import lombok.val;
import ru.work.trainsheep.DataGenerator;
import ru.work.trainsheep.send.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class FakeServerRepository extends ServerRepository {
    Random random = new Random();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    UserRegistrationData userData = null;
    DataGenerator generator = new DataGenerator();


    @Override
    public void register(UserRegistrationData user, Consumer<String> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(200, () -> {
            userData = user;
            if (random.nextDouble() > 0.2) {
                callbackSuccess.accept(user.getName());
            } else
                callbackFailure.accept(new Exception("no correct username or login"));
        });
    }

    @Override
    public void login(UserRegistrationData user, Consumer<String> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(200, () -> {
            val data = UserInfo.getInstance().getRegistrationData();
            Log.e(getClass().getSimpleName(),"data.name=" + data.getName());
            if (!data.getName().equals("")) {
                userData = data;
                callbackSuccess.accept(userData.getName());
            } else
                callbackFailure.accept(new Exception("Вы не зарегистрированы!"));
        });
    }

    @Override
    public void getVacancies(VacancyRequest request, Consumer<VacancyResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(200, () -> createVacansiesNotes(request), (notes) -> {
            if (random.nextInt(100) > 2) {
                callbackSuccess.accept(new VacancyResult(
                        notes, request.getPage(),
                        random.nextInt(100) + request.getPage() * request.getCountNotesOnPage(),
                        request.getCountNotesOnPage()));
            } else
                callbackFailure.accept(new Exception("internal error try again"));
        });
    }

    @Override
    public void getFavoriteVacancies(VacancyRequest request, Consumer<VacancyResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        getVacancies(request, callbackSuccess, callbackFailure);
    }

    @Override
    public void setFavoriteVacancy(SetFavoriteVacancyRequest request, Consumer<VacancyNote> callbackSuccess, Consumer<Exception> callbackFailure) {

    }

    @Override
    public void getCompanies(CompanyRequest request, Consumer<CompanyResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(200, () -> createCompaniesNotes(request), (notes) -> {
            callbackSuccess.accept(new CompanyResult(
                    notes, request.getPage(),
                    random.nextInt(100) + request.getPage() * request.getCountNotesOnPage(),
                    request.getCountNotesOnPage()
            ));
        });
    }

    @Override
    public void getChats(Consumer<List<ChatBlock>> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(200, generator::generateChats, callbackSuccess);
    }

    @Override
    public void getMessages(ChatRequest request, Consumer<ChatResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(200,
                () -> generator.generateChatResult(request.getPage(), request.getCountMessageOnPage()),
                callbackSuccess);
    }

    @Override
    public void getSendMessage(SendMessageRequest request, Consumer<ChatMessage> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(500,
                () -> new ChatMessage(request.getEmail(), request.getText(), new Date().getTime()),
                callbackSuccess);
    }

    @Override
    public void createVacancy(VacancyNote request, Consumer<VacancyNote> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(500,
                () -> request,
                callbackSuccess);
    }

    @NonNull
    private ArrayList<VacancyNote> createVacansiesNotes(VacancyRequest request) {
        ArrayList<VacancyNote> notes = new ArrayList<>();
        for (int i = 0; i < request.getCountNotesOnPage(); i++) {
            notes.add(generator.generateVacancyNote(request.getTags()));
        }
        return notes;
    }

    @NonNull
    private ArrayList<CompanyNote> createCompaniesNotes(CompanyRequest request) {
        ArrayList<CompanyNote> notes = new ArrayList<>();
        for (int i = 0; i < request.getCountNotesOnPage(); i++) {
            notes.add(generator.generateCompanyNote());
        }
        return notes;
    }




    private <T> void sleepAndRun(int millis, Supplier<T> runInService, Consumer<T> runInGui) {
        executor.execute(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            T t = runInService.get();
            handler.post(() -> {
                runInGui.accept(t);
            });
        });
    }

    private <T> void sleepAndRun(int millis, Runnable runInGui) {
        executor.execute(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(runInGui);
        });
    }

    @Override
    public void searchChats(SearchChatsRequest request, Consumer<List<ChatBlock>> callbackSuccess, Consumer<Exception> callbackFailure) {

    }

    @Override
    public void sendUser(UserData request, Consumer<UserData> callbackSuccess, Consumer<Exception> callbackFailure) {
        callbackSuccess.accept(request);
    }
}
