package ru.work.trainsheep;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import lombok.val;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserData;

public class ContactsActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_contacts);

        findViewById(R.id.menuBut).setOnClickListener(v -> drawer.openMenu(true));

        View.OnClickListener listener = v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse((String) ((TextView)v).getText()));
            startActivity(browserIntent);
        };

        findViewById(R.id.srcVkNikita).setOnClickListener(listener);
        findViewById(R.id.srcVkDima).setOnClickListener(listener);


    }



    @Override
    protected void onStart() {
        super.onStart();
        Util.prepareLeftIcon(this);
    }
}
