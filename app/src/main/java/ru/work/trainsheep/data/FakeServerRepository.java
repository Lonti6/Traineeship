package ru.work.trainsheep.data;

import android.os.Handler;
import android.os.Looper;
import lombok.NonNull;

import ru.work.trainsheep.send.*;
import ru.work.trainsheep.DataGenerator;
import java.util.*;
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
    public void register(UserRegistrationData user, Consumer<UserRegistrationData> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(500, () -> {
            if (random.nextDouble() > 0.05) {
                userData = new UserRegistrationData("SuperUser", "pass");
                callbackSuccess.accept(userData);
            } else
                callbackFailure.accept(new Exception("no correct username or login"));
        });
    }

    @Override
    public void login(UserRegistrationData user, Consumer<UserRegistrationData> callbackSuccess, Consumer<Exception> callbackFailure) {
        register(user, callbackSuccess, callbackFailure);
    }

    @Override
    public void getVacancys(VacancyRequest request, Consumer<VacancyResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(500, () -> createVacansysNotes(request), (notes) -> {
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
    public void getCompanys(CompanyRequest request, Consumer<CompanyResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(500, () -> createCompanysNotes(request), (notes) -> {
            if (random.nextInt(100)>2)
            {
                callbackSuccess.accept(new CompanyResult(
                        notes, request.getPage(),
                        random.nextInt(100) + request.getPage()*request.getCountNotesOnPage(),
                        request.getCountNotesOnPage()
                ));
            }
            else
                callbackFailure.accept(new Exception("internal error try again"));
        });
    }

    @Override
    public void getChats(Consumer<List<ChatBlock>> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(500, generator::generateChats, callbackSuccess);
    }

    @Override
    public void getMessages(ChatRequest request, Consumer<ChatResult> callbackSuccess, Consumer<Exception> callbackFailure) {
        sleepAndRun(500,
                () -> generator.generateChatResult(request.getPage(), request.getCountMessageOnPage(), request.getName()),
                callbackSuccess);
    }



    @NonNull
    private ArrayList<VacancyNote> createVacansysNotes(VacancyRequest request) {
        ArrayList<VacancyNote> notes = new ArrayList<>();
        for (int i = 0; i < request.getCountNotesOnPage(); i++) {
            notes.add(generator.generateVacancyNote(request.getTags()));
        }
        return notes;
    }

    @NonNull
    private ArrayList<CompanyNote> createCompanysNotes(CompanyRequest request) {
        ArrayList<CompanyNote> notes = new ArrayList<>();
        for (int i = 0; i < request.getCountNotesOnPage(); i++) {
            notes.add(generator.generateCompanyNote());
        }
        return notes;
    }


    @Override
    public boolean isLogin() {
        return userData != null;
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





}
