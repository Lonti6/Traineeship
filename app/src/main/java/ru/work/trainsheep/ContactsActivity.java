package ru.work.trainsheep;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.UserData;

public class ContactsActivity extends AppCompatActivity {
    UserData instance = UserInfo.getInstance().getData();
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
        Glide.with(this)
                .load(instance.getAvatarSrc())
                .circleCrop()
                .into((ImageView) this.findViewById(R.id.left_icon_user));
    }
}
