package ru.work.trainsheep;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class NewLeftPanel extends AppCompatActivity {
    public void prepareLeftPanel(Activity activity)
    {
        ((LinearLayout)findViewById(R.id.search_line)).setOnClickListener(v -> {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        });

        ((LinearLayout)findViewById(R.id.favorite_line)).setOnClickListener(v -> {
            Log.e("Поля \"Избранное\" ещё не существует", "Поля \"Избранное\" ещё не существует");
        });

        ((LinearLayout)findViewById(R.id.message_line)).setOnClickListener(v -> {
            Intent intent = new Intent(activity, AllChatsActivity.class);
            activity.startActivity(intent);
        });

        ((LinearLayout)findViewById(R.id.profile_line)).setOnClickListener(v -> {
            Intent intent = new Intent(activity, ProfileActivity.class);
            activity.startActivity(intent);
        });

        ((LinearLayout)findViewById(R.id.settings_line)).setOnClickListener(v -> {
            Log.e("Поля \"Настройки\" ещё не существует", "Поля \"Настройки\" ещё не существует");
        });

        ((LinearLayout)findViewById(R.id.settings_line)).setOnClickListener(v -> {
            Log.e("Поля \"Наши контакты\" ещё не существует", "Поля \"Наши контакты\" ещё не существует");
        });
    }
}
