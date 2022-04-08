package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import lombok.val;
import org.apmem.tools.layouts.FlowLayout;
import ru.work.trainsheep.data.FakeServerRepository;

public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FlowLayout flowLayout = findViewById(R.id.user_tags_field);
        flowLayout.removeAllViews();
        val server = new FakeServerRepository();



        ((TextView)findViewById(R.id.name_user)).setText(server.getRandom(server.names));

        for (int i = 0; i<((int)(Math.random()*7)+100); i++)
        {
            View layout = LayoutInflater.from(this).inflate(R.layout.tag_item, flowLayout, true);
            ((TextView) layout.findViewById(R.id.tag)).setText(server.getRandom(server.tags));
        }

        //LeftPanel.createFor(this);
        prepareLeftPanel();
    }

    public void prepareLeftPanel()
    {
        ((LinearLayout)findViewById(R.id.search_line)).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        });

        ((LinearLayout)findViewById(R.id.favorite_line)).setOnClickListener(v -> {
            Log.e("Поля \"Избранное\" ещё не существует", "Поля \"Избранное\" ещё не существует");
        });

        ((LinearLayout)findViewById(R.id.message_line)).setOnClickListener(v -> {
            Intent intent = new Intent(this, AllChatsActivity.class);
            this.startActivity(intent);
        });

        ((LinearLayout)findViewById(R.id.profile_line)).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            this.startActivity(intent);
        });

        ((LinearLayout)findViewById(R.id.settings_line)).setOnClickListener(v -> {
            Log.e("Поля \"Настройки\" ещё не существует", "Поля \"Настройки\" ещё не существует");
        });

        ((LinearLayout)findViewById(R.id.settings_line)).setOnClickListener(v -> {
            Log.e("Поля \"Наши контакты\" ещё не существует", "Поля \"Наши контакты\" ещё не существует");
        });
    }
}