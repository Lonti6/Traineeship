package ru.work.trainsheep.data;

import android.app.Activity;
import android.content.Context;
import ru.work.trainsheep.LoginResult;
import ru.work.trainsheep.UserRegistrationData;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class FakeServerRepository implements ServerRepository{
    Random random = new Random();
    ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void register(Activity activity, UserRegistrationData user, Consumer<Result<LoginResult>> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            activity.runOnUiThread(() -> {
                if(random.nextBoolean())
                    callback.accept(Result.success(new LoginResult("SuperUser")));
                else
                    callback.accept(Result.error(new Exception("no correct username or login")));
            });
        });

    }
}
