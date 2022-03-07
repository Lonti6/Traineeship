package ru.work.trainsheep;

import android.os.Bundle;
import android.view.LayoutInflater;
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

        for (int i = 0; i<((int)(Math.random()*7)+3); i++)
        {
            LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tag_item, null, false);
            ((TextView) layout.findViewById(R.id.tag)).setText(server.getRandom(server.tags));
            flowLayout.addView(layout);
        }
        LeftPanel.connect(this);
    }


}